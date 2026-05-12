package com.ppfvp.ppfvp.dto;

public record MainResponse(Double temp,
        Double feels_like,
        Double temp_min,
        Double temp_max,
        Integer pressure,
        Integer humidity,
        Integer sea_level,
        Integer grnd_level) {
    
}
