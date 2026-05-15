package com.ppfvp.ppfvp.dto;

import java.util.List;

public record PrevisaoLimpaResponse(
        String cidade,
        List<ItemPrevisaoLimpo> previsoes
) {
    public record ItemPrevisaoLimpo(
            String dataHora,
            double temp,
            double tempMin,
            double tempMax,
            String descricao,
            String icone
    ) {}
}
