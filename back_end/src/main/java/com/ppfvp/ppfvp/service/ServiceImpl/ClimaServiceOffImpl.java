package com.ppfvp.ppfvp.service.ServiceImpl;

import org.springframework.stereotype.Service;

import com.ppfvp.ppfvp.mapper.ClimaMapper;
import com.ppfvp.ppfvp.repository.ClimaRepository;

@Service
public class ClimaServiceOffImpl {
    
    private final ClimaRepository climaRepository;
    private final ClimaMapper climaMapper;

    public ClimaServiceOffImpl(ClimaRepository climaRepository) {
        this.climaRepository = climaRepository;
        this.climaMapper = new ClimaMapper(); 
    }
}
