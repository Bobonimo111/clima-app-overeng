package com.ppfvp.ppfvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppfvp.ppfvp.model.ClimaModel;

@Repository
public interface ClimaRepository extends JpaRepository<ClimaModel, Long> {
    
}
