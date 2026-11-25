package com.proemsalud.laboratory.test.simpleschemistry.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleChemistryResponse {
	
	private Long id;
	
	private String result;
	
	private String testName;
	
	private String categoryName;
	
	private Set<TestMethod> testMethods;
	
	private String testReference;
	
	private Long orderId;
	
	private BigDecimal unitPrice;
	
	private Instant createdAt;
	
	private Instant updatedAt;
	
	private Long testId;
}
