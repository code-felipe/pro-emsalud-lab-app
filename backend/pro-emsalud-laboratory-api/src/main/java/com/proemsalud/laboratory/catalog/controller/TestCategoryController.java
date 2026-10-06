package com.proemsalud.laboratory.catalog.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.catalog.dto.create.CreateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestCategoryResponse;
import com.proemsalud.laboratory.catalog.dto.update.UpdateTestCategoryRequest;
import com.proemsalud.laboratory.catalog.filter.TestCategoryFilter;
import com.proemsalud.laboratory.catalog.service.ITestCategoryService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/test-categories")
public class TestCategoryController {

	@Autowired
	private ITestCategoryService testCategoryService;

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String name) {

		List<TestCategoryResponse> categories = testCategoryService.search(name);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test categoria encontradas");
		body.put("testCategories", categories);

		return ResponseEntity.ok(body);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TestCategoryResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(testCategoryService.findById(id));
	}


	@GetMapping
	public ResponseEntity<Page<TestCategoryResponse>> list(TestCategoryFilter filter,
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(testCategoryService.findAll(filter, pageable));
	}


	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateTestCategoryRequest request) {

		TestCategoryResponse category = testCategoryService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test categoria creada exitosamente");
		body.put("testCategory", category);

		return ResponseEntity.created(URI.create("/api/test-categories/" + category.getId())).body(body);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateTestCategoryRequest request) {

		TestCategoryResponse category = testCategoryService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test categoria actualizada exitosamente");
		body.put("testCategory", category);

		return ResponseEntity.ok(body);
	}


	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Map<String, Object>> deactivate(@PathVariable Long id) {

		testCategoryService.deactivate(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test categoria desactivada exitosamente");

		return ResponseEntity.ok(body);
	}


	@PatchMapping("/{id}/activate")
	public ResponseEntity<Map<String, Object>> activate(@PathVariable Long id) {

		testCategoryService.activate(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test categoria activada exitosamente");

		return ResponseEntity.ok(body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		testCategoryService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Categoria de Test referencial borrada exitosamente");

		return ResponseEntity.ok(body);
	}

}
