package com.ppfvp.ppfvp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clouds {
    @Column(name = "clouds_all")
    private Integer all;
}
