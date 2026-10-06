package com.proemsalud.laboratory.catalog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.catalog.dto.create.CreateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestCategoryResponse;
import com.proemsalud.laboratory.catalog.dto.update.UpdateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.filter.TestCategoryFilter;


public interface ITestCategoryService {
	
	TestCategoryResponse create(CreateTestCategoryRequest request);

	TestCategoryResponse update(Long id, UpdateTestCategoryRequest request);

	TestCategoryResponse findById(Long id);

	Page<TestCategoryResponse> findAll(TestCategoryFilter filter, Pageable pageable);

	List<TestCategoryResponse> search(String name);

	void deactivate(Long id);

	void activate(Long id);
	
	void delete(Long id);
}
