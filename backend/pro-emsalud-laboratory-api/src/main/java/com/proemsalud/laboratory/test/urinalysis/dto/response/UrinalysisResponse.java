package com.proemsalud.laboratory.test.urinalysis.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.urinalysis.domain.UrinalysisResult;

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
public class UrinalysisResponse {

	private Long id;
	
	private UrinalysisResult urinalysisResult;
	
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
