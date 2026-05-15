package com.ppfvp.ppfvp.dto;

import java.util.List;

public record ForecastResponse(
        String cod,
        int message,
        int cnt,
        List<ForecastItem> list,
        City city
) {
    public record ForecastItem(
            Long dt,
            MainResponse main,
            List<WeatherResponse> weather,
            CloudsResponse clouds,
            WindResponse wind,
            int visibility,
            double pop,
            SysResponse sys,
            String dt_txt
    ) {}

    public record City(
            Long id,
            String name,
            CoordResponse coord,
            String country,
            int population,
            int timezone,
            Long sunrise,
            Long sunset
    ) {}
}