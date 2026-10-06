package com.proemsalud.laboratory.catalog.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.proemsalud.laboratory.catalog.enumerate.TestType;

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
public class TestResponse {
	
	private Long id;

	private String name;

	private TestType testType;

	private String reference;

	private BigDecimal price;

	private Boolean active;

	private Instant createdAt;

	private Instant updatedAt;

	private Long testCategoryId;

	private String testCategoryName; // útil para tablas/selects sin otra llamada al backend
}
