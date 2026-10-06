package com.proemsalud.laboratory.catalog.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.catalog.dto.create.CreateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestCategoryResponse;

@Component
public class TestCategoryMapper {
	
	public TestCategory toEntity(CreateTestCategoryRequest request) {
		return TestCategory.builder()
			.name(request.getName())
			.build();
	}

	public TestCategoryResponse toResponse(TestCategory category) {
		return TestCategoryResponse.builder()
			.id(category.getId())
			.name(category.getName())
			.active(category.getActive())
			.createdAt(category.getCreatedAt())
			.updatedAt(category.getUpdatedAt())
			.build();
	}
}
