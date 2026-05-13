package com.ppfvp.ppfvp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppfvp.ppfvp.service.ClimaService;

@RestController
@RequestMapping("/clima")
public class ClimaController {
    private final ClimaService climaService;
    
    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

}
