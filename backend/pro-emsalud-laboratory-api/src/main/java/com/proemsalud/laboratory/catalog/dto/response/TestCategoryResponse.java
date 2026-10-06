package com.proemsalud.laboratory.catalog.dto.response;
import java.time.Instant;

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
public class TestCategoryResponse {
	
	private Long id;

	private String name;

	private Boolean active;

	private Instant createdAt;

	private Instant updatedAt;
}
