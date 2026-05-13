package com.ppfvp.ppfvp.service;

import java.util.List;

import com.ppfvp.ppfvp.dto.ClimaResponse;

public interface ClimaServiceOff {
    ClimaResponse getForecastByCity(String cityName);

    ClimaResponse getForecastByCoord(double lat, double lon);

    ClimaResponse getWeatherByCity(String cityName);

    ClimaResponse getWeatherByCoord(double lat, double lon);

    void updateWeatherDatabase(ClimaResponse dto);

    void updateWeatherDatabase(List<ClimaResponse> climaResponses);
}
