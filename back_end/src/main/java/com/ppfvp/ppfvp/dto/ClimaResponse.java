package com.ppfvp.ppfvp.dto;

import java.util.List;


public record ClimaResponse(
    CoordResponse coord,
    List<WeatherResponse> weather,
        String base,
        MainResponse main,
        Integer visibility,
        WindResponse wind,
        CloudsResponse clouds,
        Long dt,
        SysResponse sys,
        Integer timezone,
        Long id,
        String name,
        Integer cod
) {}
