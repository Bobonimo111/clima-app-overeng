package com.ppfvp.ppfvp.service;

import com.ppfvp.ppfvp.dto.ClimaResponse;

public interface ClimaService {
    ClimaResponse getForecastByCity(String cityName);

    ClimaResponse getForecastByCoord(double lat, double lon);

    ClimaResponse getWeatherByCity(String cityName);

    ClimaResponse getWeatherByCoord(double lat, double lon);
}
