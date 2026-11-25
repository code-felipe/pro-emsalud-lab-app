package com.proemsalud.laboratory.test.stool.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.stool.domain.StoolResult;


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
public class StoolResponse {
	
	private Long id;
	
	private StoolResult stoolResult;
	
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
