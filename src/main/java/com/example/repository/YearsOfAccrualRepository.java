package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.YearsOfAccrual;

@Repository
public interface YearsOfAccrualRepository extends JpaRepository<YearsOfAccrual,Long> {

}
