package com.proemsalud.laboratory.catalog.mapper;


import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.catalog.dto.create.CreateTestRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestResponse;

@Component
public class TestMapper {
	
	
	public Test toEntity(CreateTestRequest request, TestCategory category) {
		return Test.builder()
			.name(request.getName())
			.testType(request.getTestType())
			.reference(normalizeBlank(request.getReference()))
			.price(request.getPrice())
			.category(category)
			.build();
	}

	public TestResponse toResponse(Test test) {
		return TestResponse.builder()
			.id(test.getId())
			.name(test.getName())
			.testType(test.getTestType())
			.reference(test.getReference())
			.price(test.getPrice())
			.active(test.getActive())
			.createdAt(test.getCreatedAt())
			.updatedAt(test.getUpdatedAt())
			.testCategoryId(test.getCategory().getId())
			.testCategoryName(test.getCategory().getName())
			.build();
	}
	
	private String normalizeBlank(String value) {
		return (value == null || value.isBlank()) ? null : value;
	}
}
