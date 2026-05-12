package com.ppfvp.ppfvp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "clima")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClimaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String base;
    private Integer visibility;
    private Long dt;
    private Integer timezone;
    private Integer cod;


    @Embedded
    private Coord coord;

    @Embedded
    private Main main;

    @Embedded
    private Clouds clouds;

    @Embedded
    private Sys sys;

    @Embedded
    private Wind wind;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "clima_id")
    private List<Weather> weather;

}
