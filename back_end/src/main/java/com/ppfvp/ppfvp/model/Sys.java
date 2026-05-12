package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Sys {
    private Integer type;
    private Long sunrise;
    private Long sunset;
}
