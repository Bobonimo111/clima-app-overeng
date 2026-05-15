package com.ppfvp.ppfvp.client;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class WeatherFeignConfig {

    @Value("${weather.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("appid", apiKey);
            requestTemplate.query("units", "metric");
            requestTemplate.query("lang", "pt_br");
        };
    }
}
