package com.ppfvp.ppfvp.service.ServiceImpl;
import com.ppfvp.ppfvp.client.WeatherClient;
import com.ppfvp.ppfvp.dto.ClimaResponse;
import com.ppfvp.ppfvp.dto.ForecastResponse;
import com.ppfvp.ppfvp.dto.PrevisaoLimpaResponse;
import com.ppfvp.ppfvp.mapper.ClimaMapper;
import com.ppfvp.ppfvp.mapper.PrevisaoMapper;
import com.ppfvp.ppfvp.service.ClimaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClimaServiceImpl implements ClimaService {

    private final ClimaServiceOffImpl offlineClient;
    private final WeatherClient cliente;
    private final ClimaMapper climaMapper;
    private final PrevisaoMapper previsaoMapper;

    private static final long HORAS_VALIDADE = 12;

    @Override
    @Cacheable(value = "climaCache", key = "#cityName.toLowerCase()")
    public ClimaResponse getWeatherByCity(String cityName) {
        log.info("Processando requisição de clima (Weather) para a cidade: {}", cityName);
        return orquestrarFluxoDeDados(
                () -> offlineClient.getWeatherByCity(cityName),
                () -> cliente.getWeatherByCity(cityName)
        );
    }

    @Override
    @Cacheable(value = "climaCache", key = "#lat + ',' + #lon")
    public ClimaResponse getWeatherByCoord(double lat, double lon) {
        log.info("Processando requisição de clima (Weather) para coordenadas: lat={}, lon={}", lat, lon);
        return orquestrarFluxoDeDados(
                () -> offlineClient.getWeatherByCoord(lat, lon),
                () -> cliente.getWeatherByCoord(lat, lon)
        );
    }

    @Override
    @Cacheable(value = "forecastCache", key = "#cityName.toLowerCase()")
    public PrevisaoLimpaResponse getForecastByCity(String cityName) {
        log.info("Processando requisição de previsão (Forecast) para a cidade: {}", cityName);

        ForecastResponse rawData = cliente.getForecastByCity(cityName);
        return previsaoMapper.limparPrevisaoParaFrontend(rawData);
    }

    @Override
    @Cacheable(value = "forecastCache", key = "#lat + ',' + #lon")
    public PrevisaoLimpaResponse getForecastByCoord(double lat, double lon) {
        log.info("Processando requisição de previsão (Forecast) para coordenadas: lat={}, lon={}", lat, lon);

        ForecastResponse rawData = cliente.getForecastByCoord(lat, lon);
        return previsaoMapper.limparPrevisaoParaFrontend(rawData);
    }

    private ClimaResponse orquestrarFluxoDeDados(
            java.util.function.Supplier<ClimaResponse> chamadaOffline,
            java.util.function.Supplier<ClimaResponse> chamadaApi) {

        ClimaResponse dadosLocais = null;
        boolean bancoDesatualizado = true;

        try {
            dadosLocais = chamadaOffline.get();
            if (dadosLocais != null) {
                bancoDesatualizado = isBancoDesatualizado(dadosLocais.dt());
            }
        } catch (Exception e) {
            log.debug("Nenhum dado local encontrado ou erro ao acessar o banco.");
        }

        if (dadosLocais != null && !bancoDesatualizado) {
            log.info("Retornando dados do banco local (atualizado há menos de 12h).");
            return dadosLocais;
        }

        try {
            log.info("Dados desatualizados ou inexistentes. Fazendo chamada à API externa...");
            ClimaResponse dadosDaApi = chamadaApi.get();

            try {
                offlineClient.updateWeatherDatabase(dadosDaApi);
            } catch (Exception ex) {
                log.error("Erro ao tentar atualizar o banco local com os dados da API.", ex);
            }

            return dadosDaApi;

        } catch (FeignException e) {
            log.error("Falha ao comunicar com a API Externa. Status HTTP: {}", e.status());

            if (dadosLocais != null) {
                log.warn("ACIONANDO FALLBACK: Retornando dados obsoletos do banco para manter a aplicação online.");
                return dadosLocais;
            }

            throw new RuntimeException("Serviço indisponível: API externa offline e banco local vazio.");
        }
    }

    private boolean isBancoDesatualizado(Long dataMedicaoSegundos) {
        if (dataMedicaoSegundos == null) return true;

        Instant dataMedicao = Instant.ofEpochSecond(dataMedicaoSegundos);
        Instant limiteValidade = Instant.now().minus(HORAS_VALIDADE, ChronoUnit.HOURS);

        return dataMedicao.isBefore(limiteValidade);
    }
}