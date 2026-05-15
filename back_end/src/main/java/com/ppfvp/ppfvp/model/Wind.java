package com.ppfvp.ppfvp.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wind {
    private Double speed;
    private Integer deg;
    private Double gust;
}
