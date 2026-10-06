package com.proemsalud.laboratory.catalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.catalog.dto.create.CreateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestCategoryResponse;
import com.proemsalud.laboratory.catalog.dto.update.UpdateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.filter.TestCategoryFilter;
import com.proemsalud.laboratory.catalog.mapper.TestCategoryMapper;
import com.proemsalud.laboratory.catalog.repository.TestCategoryRepository;
import com.proemsalud.laboratory.catalog.specification.TestCategorySpecification;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;

@Service
public class TestCategoryServiceImpl implements ITestCategoryService {

	@Autowired
	private TestCategoryRepository testCategoryRepository;

	@Autowired
	private TestCategoryMapper testCategoryMapper;

	@Override
	public TestCategoryResponse create(CreateTestCategoryRequest request) {

		TestCategory category = testCategoryMapper.toEntity(request);
		TestCategory saved = testCategoryRepository.save(category);

		return testCategoryMapper.toResponse(saved);
	}

	@Override
	public TestCategoryResponse update(Long id, UpdateTestCategoryRequest request) {

		TestCategory category = testCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La categoria no existe con id: " + id));

		category.setName(request.getName());

		TestCategory updated = testCategoryRepository.save(category);

		return testCategoryMapper.toResponse(updated);
	}

	@Override
	public TestCategoryResponse findById(Long id) {

		TestCategory category = testCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La categoria no existe con id: " + id));

		return testCategoryMapper.toResponse(category);
	}

	@Override
	public Page<TestCategoryResponse> findAll(TestCategoryFilter filter, Pageable pageable) {

		return testCategoryRepository.findAll(TestCategorySpecification.withFilters(filter), pageable)
				.map(testCategoryMapper::toResponse);
	}

	@Override
	public List<TestCategoryResponse> search(String name) {

		String safeName = name != null ? name : "";

		return testCategoryRepository.findByNameContainingIgnoreCaseAndActiveTrue(safeName).stream()
				.map(testCategoryMapper::toResponse)
				.toList();
	}

	@Override
	public void deactivate(Long id) {

		TestCategory category = testCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La categoria no existe con id: " + id));

		category.setActive(false);

		testCategoryRepository.save(category);
	}

	@Override
	public void activate(Long id) {

		TestCategory category = testCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La categoria no existe con id: " + id));

		category.setActive(true);

		testCategoryRepository.save(category);
	}

	
	@Override
	public void delete(Long id) {
		TestCategory test = testCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La categoria test referencial no existe con id: " + id));
		testCategoryRepository.delete(test);
		
	}

	
	
	
}
