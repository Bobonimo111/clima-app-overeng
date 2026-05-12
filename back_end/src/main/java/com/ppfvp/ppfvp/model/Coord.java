package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Coord {
    private Double lon;
    private Double lat;
}
