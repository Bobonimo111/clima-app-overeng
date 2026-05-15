package com.ppfvp.ppfvp.dto;


public record ClimaRequest(
        String city,
        Double lat,
        Double lon
) {
    public boolean isBuscaPorCidade() {
        return city != null && !city.trim().isEmpty();
    }
}