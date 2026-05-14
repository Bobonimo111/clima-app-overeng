package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coord {
    private Double lon;
    private Double lat;
}
