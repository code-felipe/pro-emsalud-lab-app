package com.proemsalud.laboratory.test.urinalysis.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrinalysisFilter {
	
	private String search;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
