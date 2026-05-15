package com.ppfvp.ppfvp.service.ServiceImpl;

import com.ppfvp.ppfvp.dto.ClimaResponse;
import com.ppfvp.ppfvp.mapper.ClimaMapper;
import com.ppfvp.ppfvp.model.ClimaModel;
import com.ppfvp.ppfvp.repository.ClimaRepository;
import com.ppfvp.ppfvp.service.ClimaServiceOff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClimaServiceOffImpl implements ClimaServiceOff {

    private final ClimaRepository repository;
    private final ClimaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ClimaResponse getWeatherByCity(String cityName) {
        return repository.findByNameIgnoreCase(cityName)
                .map(mapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ClimaResponse getWeatherByCoord(double lat, double lon) {
        double margin = 0.01;
        return repository.findByCoordLatBetweenAndCoordLonBetween(lat - margin, lat + margin, lon - margin, lon + margin)
                .stream()
                .findFirst()
                .map(mapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ClimaResponse getForecastByCity(String cityName) {
        return getWeatherByCity(cityName);
    }

    @Override
    @Transactional(readOnly = true)
    public ClimaResponse getForecastByCoord(double lat, double lon) {
        return getWeatherByCoord(lat, lon);
    }

    @Override
    @Transactional
    public void updateWeatherDatabase(ClimaResponse dto) {
        if (dto == null || dto.name() == null) return;

        log.debug("Atualizando banco de dados para a cidade: {}", dto.name());
        ClimaModel modelParaSalvar = prepararModeloParaSalvar(dto);
        repository.save(modelParaSalvar);
    }

    @Override
    @Transactional
    public void updateWeatherDatabase(List<ClimaResponse> climaResponses) {
        if (climaResponses == null || climaResponses.isEmpty()) return;

        log.info("Executando batch update no banco para {} registros...", climaResponses.size());
        List<ClimaModel> modelosParaSalvar = climaResponses.stream()
                .map(this::prepararModeloParaSalvar)
                .collect(Collectors.toList());

        repository.saveAll(modelosParaSalvar);
    }

    private ClimaModel prepararModeloParaSalvar(ClimaResponse response) {
        Optional<ClimaModel> existente = repository.findByNameIgnoreCase(response.name());

        if (existente.isPresent()) {
            ClimaModel model = existente.get();
            mapper.updateModelFromResponse(response, model);
            return model;
        } else {
            return mapper.toModel(response);
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void expurgarDadosAntigosDoBanco() {
        log.info("[JOB LIMPEZA] Iniciando exclusão de dados com mais de 5 dias...");

        long limiteCincoDias = Instant.now().minus(5, ChronoUnit.DAYS).getEpochSecond();

        try {
            repository.deleteDadosMaisVelhosQue(limiteCincoDias);
            log.info("[JOB LIMPEZA] Banco de dados limpo com sucesso.");
        } catch (Exception e) {
            log.error("[JOB LIMPEZA] Erro ao tentar limpar dados antigos do banco.", e);
        }
    }
}
