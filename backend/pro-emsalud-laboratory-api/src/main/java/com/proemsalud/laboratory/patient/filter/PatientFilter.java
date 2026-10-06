package com.proemsalud.laboratory.patient.filter;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientFilter {
	
	private String search;// Group full name y code
	
	private String gender;
	
	private LocalDate dateOfBirth;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
