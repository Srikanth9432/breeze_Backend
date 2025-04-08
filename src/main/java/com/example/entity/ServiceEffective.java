// ServiceEffective.java
package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class ServiceEffective {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "service_effective_date", nullable = false)
	private LocalDateTime serviceEffectiveDate;
	private Boolean seriveDataAvailable;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "years_of_vesting_id")
	private YearsOfVesting yearsOfVesting;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "years_of_accrual_id")
	private YearsOfAccrual yearsOfAccrual;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vesting_percentage_id")
	private VestingPercentage vestingPercentage;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "covered_compensation_id")
	private CoveredCompensation coveredCompensation;
}
