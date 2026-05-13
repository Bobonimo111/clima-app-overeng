package com.ppfvp.ppfvp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppfvp.ppfvp.dto.ClimaResponse;


@FeignClient(value = "climaclient", url = "https://api.openweathermap.org/data/2.5/")
public interface ClimaClient {
    @GetMapping("/weather")
    ClimaResponse getClima( @RequestParam("q") String cidade, @RequestParam("appid") String apiKey);
}
