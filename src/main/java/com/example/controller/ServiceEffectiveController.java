package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.ServiceEffectiveDTO;
import com.example.service.ServiceEffectiveService;

@RestController
@RequestMapping("/home")
public class ServiceEffectiveController {

	@Autowired
	private ServiceEffectiveService serviceEffectiveService;

	@GetMapping
	public ResponseEntity<?> getAllServiceEffectives(
			@RequestParam(name = "serviceDataAvailable", defaultValue = "false") Boolean serviceDataAvailable) {

		// Log the serviceDataAvailable value for debugging
		System.out.println("Received request with serviceDataAvailable: " + serviceDataAvailable);

		// If serviceDataAvailable is false, fetch and return the data
		if (!serviceDataAvailable) {
			List<ServiceEffectiveDTO> serviceEffectives = serviceEffectiveService
					.getServiceEffectivesWithFullData(serviceDataAvailable);
			return ResponseEntity.ok(serviceEffectives);
		}
		return ResponseEntity.ok("no data available");
	}
	

	// Endpoint to create or update ServiceEffective data
	@PostMapping("/create")
	public ResponseEntity<ServiceEffectiveDTO> createServiceEffective(
			@RequestBody ServiceEffectiveDTO serviceEffectiveDTO) {
		ServiceEffectiveDTO savedDTO = serviceEffectiveService.saveServiceEffective(serviceEffectiveDTO);
		return ResponseEntity.ok(savedDTO);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteServiceEffective(
			@RequestParam(name = "serviceDataAvailable", defaultValue = "false") Boolean serviceDataAvailable) {
		Boolean b = serviceEffectiveService.deleteServiceEffective(serviceDataAvailable);
		if (b) {
			return ResponseEntity.ok("its deleted");

		} else {
			return ResponseEntity.ok("its not deleted");
		}
	}
}
