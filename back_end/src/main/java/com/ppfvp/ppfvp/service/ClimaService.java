package com.ppfvp.ppfvp.service;

import com.ppfvp.ppfvp.dto.ClimaResponse;
import com.ppfvp.ppfvp.dto.PrevisaoLimpaResponse;

public interface ClimaService {
    PrevisaoLimpaResponse getForecastByCity(String cityName);

    PrevisaoLimpaResponse getForecastByCoord(double lat, double lon);

    ClimaResponse getWeatherByCity(String cityName);

    ClimaResponse getWeatherByCoord(double lat, double lon);
}
