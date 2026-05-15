package com.ppfvp.ppfvp.mapper;

import com.ppfvp.ppfvp.dto.ForecastResponse;
import com.ppfvp.ppfvp.dto.PrevisaoLimpaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PrevisaoMapper {

    public PrevisaoLimpaResponse limparPrevisaoParaFrontend(ForecastResponse rawResponse) {
        if (rawResponse == null || rawResponse.list() == null) return null;

        List<PrevisaoLimpaResponse.ItemPrevisaoLimpo> itensLimpos = rawResponse.list().stream()
                .map(this::extrairItemLimpo)
                .toList();

        String nomeCidade = Optional.ofNullable(rawResponse.city())
                .map(ForecastResponse.City::name)
                .orElse("Desconhecida");

        return new PrevisaoLimpaResponse(nomeCidade, itensLimpos);
    }

    private PrevisaoLimpaResponse.ItemPrevisaoLimpo extrairItemLimpo(ForecastResponse.ForecastItem item) {
        var weatherInfo = item.weather() != null && !item.weather().isEmpty()
                ? item.weather().get(0)
                : null;

        return new PrevisaoLimpaResponse.ItemPrevisaoLimpo(
                item.dt_txt(),
                item.main().temp(),
                item.main().temp_min(),
                item.main().temp_max(),
                weatherInfo != null ? weatherInfo.description() : "",
                weatherInfo != null ? weatherInfo.icon() : ""
        );
    }
}