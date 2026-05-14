package com.ppfvp.ppfvp.client;

import com.ppfvp.ppfvp.dto.ForecastResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppfvp.ppfvp.dto.ClimaResponse;

@FeignClient(
        name = "weatherClient",
        url = "${weather.api.base-url}",
        configuration = WeatherFeignConfig.class
)
public interface WeatherClient {

    @GetMapping("/weather")
    ClimaResponse getWeatherByCity(@RequestParam("q") String cityName);

    @GetMapping("/weather")
    ClimaResponse getWeatherByCoord(@RequestParam("lat") double lat, @RequestParam("lon") double lon);

    @GetMapping("/forecast")
    ForecastResponse getForecastByCity(@RequestParam("q") String cityName);

    @GetMapping("/forecast")
    ForecastResponse getForecastByCoord(@RequestParam("lat") double lat, @RequestParam("lon") double lon);
}
