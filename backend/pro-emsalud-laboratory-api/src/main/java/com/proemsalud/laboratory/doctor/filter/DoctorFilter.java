package com.proemsalud.laboratory.doctor.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorFilter {
	
	private String firstName;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
