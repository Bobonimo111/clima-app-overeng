package com.ppfvp.ppfvp.service.ServiceImpl;

import org.springframework.stereotype.Service;

import com.ppfvp.ppfvp.client.ClimaClient;
import com.ppfvp.ppfvp.service.ClimaServiceOff;

@Service
public class ClimaServiceImpl {

    private final ClimaServiceOff offlineClient;
    private final ClimaClient cliente;

    public ClimaServiceImpl(ClimaServiceOff offlineClient, ClimaClient cliente) {
        this.offlineClient = offlineClient;
        this.cliente = cliente;
    }

}
