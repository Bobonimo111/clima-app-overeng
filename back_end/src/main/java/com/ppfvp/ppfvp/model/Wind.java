package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Wind {
    private Double speed;
    private Integer deg;
    private Double gust;
}
