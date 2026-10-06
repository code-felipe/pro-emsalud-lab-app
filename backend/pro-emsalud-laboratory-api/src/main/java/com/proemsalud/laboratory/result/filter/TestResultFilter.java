package com.proemsalud.laboratory.result.filter;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestResultFilter {

	private String search;
	
	private BigDecimal priceMin;

	private BigDecimal priceMax;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
