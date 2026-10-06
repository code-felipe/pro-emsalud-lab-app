package com.proemsalud.laboratory.catalog.filter;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TestFilter {
	
	private String name;

	private BigDecimal priceMin;

	private BigDecimal priceMax;

	private Boolean active;

	private String testCategoryName;

	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
