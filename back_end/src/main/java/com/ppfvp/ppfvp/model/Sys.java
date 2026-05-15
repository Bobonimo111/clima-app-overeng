package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sys {
    private Integer type;
    private String country;
    private Long sunrise;
    private Long sunset;
}
