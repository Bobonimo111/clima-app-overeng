package com.ppfvp.ppfvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ppfvp.ppfvp.model.ClimaModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClimaRepository extends JpaRepository<ClimaModel, Long> {

    Optional<ClimaModel> findByNameIgnoreCase(String name);

    List<ClimaModel> findByCoordLatBetweenAndCoordLonBetween(
            Double latMin,
            Double latMax,
            Double lonMin,
            Double lonMax
    );

    @Modifying
    @Query("DELETE FROM ClimaModel c WHERE c.dt < :timestampLimite")
    void deleteDadosMaisVelhosQue(@Param("timestampLimite") Long timestampLimite);

}