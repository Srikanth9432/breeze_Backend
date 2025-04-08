package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CoveredCompensation;

@Repository
public interface CoveredCompensationRepository extends JpaRepository<CoveredCompensation, Long>{

}
