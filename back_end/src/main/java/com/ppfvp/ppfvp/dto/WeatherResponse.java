package com.ppfvp.ppfvp.dto;

public record WeatherResponse(Integer id,
        String main,
        String description,
        String icon) {
}
