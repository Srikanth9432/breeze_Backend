package com.example.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.CoveredCompensationDTO;
import com.example.DTO.ServiceEffectiveDTO;
import com.example.DTO.VestingPercentageDTO;
import com.example.DTO.YearsOfAccrualDTO;
import com.example.DTO.YearsOfVestingDTO;
import com.example.config.FileSaveUtil;
import com.example.entity.CoveredCompensation;
import com.example.entity.ServiceEffective;
import com.example.entity.VestingPercentage;
import com.example.entity.YearsOfAccrual;
import com.example.entity.YearsOfVesting;
import com.example.exception.EntityNotFoundException;
import com.example.repository.ServiceEffectiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceEffectiveService {

	@Autowired
	private ServiceEffectiveRepository serviceEffectiveRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private FileSaveUtil fileSaveUtil;

	// Get all ServiceEffective records with all related entities when
	// serviceDataAvailable = false
	public List<ServiceEffectiveDTO> getServiceEffectivesWithFullData(Boolean serviceDataAvailable) {
		// Fetch all ServiceEffective entities where serviceDataAvailable is false
		List<ServiceEffective> serviceEffectives = serviceEffectiveRepository
				.findBySeriveDataAvailable(serviceDataAvailable);

		// Map the entities to DTOs and include the full data (related entities like
		// YearsOfAccrual, etc.)
		return serviceEffectives.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Helper method to map the entity to DTO
	private ServiceEffectiveDTO mapToDTO(ServiceEffective serviceEffective) {
		ServiceEffectiveDTO dto = modelMapper.map(serviceEffective, ServiceEffectiveDTO.class);

		// Manually mapping the dependent fields (related entities)
		if (serviceEffective.getCoveredCompensation() != null) {
			dto.setCoveredCompensation(
					modelMapper.map(serviceEffective.getCoveredCompensation(), CoveredCompensationDTO.class));
		}

		if (serviceEffective.getYearsOfAccrual() != null) {
			dto.setYearsOfAccrual(modelMapper.map(serviceEffective.getYearsOfAccrual(), YearsOfAccrualDTO.class));
		}

		if (serviceEffective.getYearsOfVesting() != null) {
			dto.setYearsOfVesting(modelMapper.map(serviceEffective.getYearsOfVesting(), YearsOfVestingDTO.class));
		}

		if (serviceEffective.getVestingPercentage() != null) {
			dto.setVestingPercentage(
					modelMapper.map(serviceEffective.getVestingPercentage(), VestingPercentageDTO.class));
		}

		return dto;
	}

	public ServiceEffectiveDTO saveServiceEffective(ServiceEffectiveDTO serviceEffectiveDTO) {
		// Map the ServiceEffectiveDTO to the ServiceEffective entity
		ServiceEffective serviceEffective = modelMapper.map(serviceEffectiveDTO, ServiceEffective.class);

		if (Boolean.TRUE.equals(serviceEffective.getSeriveDataAvailable())) {
			throw new EntityNotFoundException("Service data is not available. Cannot save again.");
		}

		serviceEffective.setServiceEffectiveDate(LocalDateTime.now());
		// Check if dependent entities are provided, and map them if necessary.
		// Example for CoveredCompensation
		if (serviceEffectiveDTO.getCoveredCompensation() != null) {
			CoveredCompensation coveredCompensation = modelMapper.map(serviceEffectiveDTO.getCoveredCompensation(),
					CoveredCompensation.class);
			serviceEffective.setCoveredCompensation(coveredCompensation);
		}

		// Example for YearsOfAccrual
		if (serviceEffectiveDTO.getYearsOfAccrual() != null) {
			YearsOfAccrual yearsOfAccrued = modelMapper.map(serviceEffectiveDTO.getYearsOfAccrual(),
					YearsOfAccrual.class);
			serviceEffective.setYearsOfAccrual(yearsOfAccrued);
		}

		// Example for VestingPercentage
		if (serviceEffectiveDTO.getVestingPercentage() != null) {
			VestingPercentage coveredPercentage = modelMapper.map(serviceEffectiveDTO.getVestingPercentage(),
					VestingPercentage.class);
			serviceEffective.setVestingPercentage(coveredPercentage);
		}

		// Example for VestingPercentage
		if (serviceEffectiveDTO.getYearsOfVesting() != null) {
			YearsOfVesting yearsOfVesting = modelMapper.map(serviceEffectiveDTO.getYearsOfVesting(),
					YearsOfVesting.class);
			serviceEffective.setYearsOfVesting(yearsOfVesting);
		}
		// Save the ServiceEffective entity with its related entities
		serviceEffective = serviceEffectiveRepository.save(serviceEffective);

		ServiceEffectiveDTO savedDto = modelMapper.map(serviceEffective, ServiceEffectiveDTO.class);
		try {
			String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedDto);
			fileSaveUtil.saveToFile(json);
			// Customize this as per your format
		} catch (IOException e) {
			e.printStackTrace(); // or log the error
		}

		return savedDto;

		// Map the saved ServiceEffective entity back to DTO

	}

	public Boolean deleteServiceEffective(Boolean serviceDataAvailable) {

		// Fetch all ServiceEffective entities where serviceDataAvailable is false
		List<ServiceEffective> serviceEffectives = serviceEffectiveRepository
				.findBySeriveDataAvailable(serviceDataAvailable);
		if (!serviceEffectives.isEmpty()) {
			serviceEffectiveRepository.deleteAll(serviceEffectives);

			return true;
		}
		return false;
	}

}