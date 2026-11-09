package com.proemsalud.laboratory.test.simpleschemistry.filter;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleChemistryFilter {

	private String search;
	
	private BigDecimal priceMin;

	private BigDecimal priceMax;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
