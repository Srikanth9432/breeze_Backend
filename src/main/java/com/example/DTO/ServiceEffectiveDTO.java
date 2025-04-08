package com.example.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceEffectiveDTO {

	private LocalDateTime serviceEffectiveDate;
	private Boolean seriveDataAvailable;

	private YearsOfVestingDTO yearsOfVesting;

	private YearsOfAccrualDTO yearsOfAccrual;

	private VestingPercentageDTO vestingPercentage;

	private CoveredCompensationDTO coveredCompensation;

}
