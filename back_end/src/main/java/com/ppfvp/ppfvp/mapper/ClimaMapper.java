package com.ppfvp.ppfvp.mapper;

import org.springframework.stereotype.Component;

import com.ppfvp.ppfvp.dto.ClimaResponse;
import com.ppfvp.ppfvp.model.ClimaModel;

@Component
public class ClimaMapper {
    public ClimaResponse modelToDto(ClimaModel model){
        if(model == null){
            return null;
        }

        return null;
    }

    public ClimaModel dtoToClima(ClimaResponse dto){
        if(dto == null){
            return null;
        }

        return null;
    }
}
