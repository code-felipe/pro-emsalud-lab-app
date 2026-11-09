package com.proemsalud.laboratory.test.stool.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoolFilter {
	
	private String search;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
