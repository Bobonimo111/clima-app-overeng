package com.ppfvp.ppfvp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Weather {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInternal;

    private String main;
    private String description;
    private String icon;
}

