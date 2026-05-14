package com.ppfvp.ppfvp.controller;

import com.ppfvp.ppfvp.dto.ClimaRequest;
import com.ppfvp.ppfvp.dto.ClimaResponse;
import com.ppfvp.ppfvp.dto.ForecastResponse;
import com.ppfvp.ppfvp.dto.PrevisaoLimpaResponse;
import com.ppfvp.ppfvp.service.ClimaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClimaController {

    private final ClimaService climaService;

    @GetMapping("/climas")
    public ResponseEntity<ClimaResponse> getClimaAtual(@Valid ClimaRequest request) {
        log.info("Buscando clima atual para a requisição: {}", request);

        ClimaResponse response = request.isBuscaPorCidade()
                ? climaService.getWeatherByCity(request.city())
                : climaService.getWeatherByCoord(request.lat(), request.lon());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/previsoes")
    public ResponseEntity<PrevisaoLimpaResponse> getPrevisaoDetalhada(@Valid ClimaRequest request) {
        log.info("Buscando previsão detalhada para a requisição: {}", request);

        PrevisaoLimpaResponse response = request.isBuscaPorCidade()
                ? climaService.getForecastByCity(request.city())
                : climaService.getForecastByCoord(request.lat(), request.lon());

        return ResponseEntity.ok(response);
    }
}