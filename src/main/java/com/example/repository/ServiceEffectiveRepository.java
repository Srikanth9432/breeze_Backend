package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ServiceEffective;

@Repository
public interface ServiceEffectiveRepository extends JpaRepository<ServiceEffective, Long> {
	 List<ServiceEffective> findBySeriveDataAvailable(Boolean serviceDataAvailable);
}
