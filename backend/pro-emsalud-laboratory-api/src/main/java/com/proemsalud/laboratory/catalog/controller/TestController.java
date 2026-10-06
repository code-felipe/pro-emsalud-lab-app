package com.proemsalud.laboratory.catalog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
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

import com.proemsalud.laboratory.catalog.dto.create.CreateTestRequest;
import com.proemsalud.laboratory.catalog.dto.response.TestResponse;
import com.proemsalud.laboratory.catalog.dto.update.UpdateTestRequest;
import com.proemsalud.laboratory.catalog.filter.TestFilter;
import com.proemsalud.laboratory.catalog.service.ITestService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/tests")
public class TestController {

	@Autowired
	private ITestService testService;

	@GetMapping("/{id}")
	public ResponseEntity<TestResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(testService.findById(id));
	}

	@GetMapping
	public ResponseEntity<Page<TestResponse>> list(TestFilter filter,
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(testService.findAll(filter, pageable));
	}


	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateTestRequest request) {

		TestResponse test = testService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test referencial creado exitosamente");
		body.put("test", test);

		return ResponseEntity.created(URI.create("/api/tests/" + test.getId())).body(body);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> search(
	        @RequestParam String query) {

	    List<TestResponse> tests = testService.searchTests(query);

	    Map<String, Object> body = new HashMap<>();
	    body.put("message", "Tests referencial encontrados");
	    body.put("tests", tests);

	    return ResponseEntity.ok(body);
	}

	
	// === Search by name (+ category opcional) ===
//	@GetMapping("/search")
//	public ResponseEntity<Map<String, Object>> search(
//	        @RequestParam(required = false) String name,
//	        @RequestParam(required = false) String categoryName) {
//
//	    List<TestResponse> tests;
//
//	    boolean hasName = name != null && !name.isBlank();
//	    boolean hasCategory = categoryName != null && !categoryName.isBlank();
//
//	    if (hasName && hasCategory) {
//	        tests = testService.searchByNameAndCategoryName(name, categoryName);
//	    } else if (hasName) {
//	        tests = testService.searchByName(name);
//	    } else if (hasCategory) {
//	        tests = testService.searchByCategoryName(categoryName);
//	    } else {
//	        tests = List.of();
//	    }
//
//	    Map<String, Object> body = new HashMap<>();
//	    body.put("message", "Tests referencial encontrados");
//	    body.put("tests", tests);
//
//	    return ResponseEntity.ok(body);
//	}

//	@GetMapping("/search")
//	public ResponseEntity<Map<String, Object>> search(
//			@RequestParam String name,
//			@RequestParam(required = false) Long categoryId) {
//
//		List<TestResponse> tests = categoryId != null ? testService.searchByNameAndCategoryId(name, categoryId)
//				: testService.searchByName(name);
//
//		Map<String, Object> body = new HashMap<>();
//		body.put("message", "Tests referencial encontrados");
//		body.put("tests", tests);
//
//		return ResponseEntity.ok(body);
//	}
	

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateTestRequest request) {

		TestResponse test = testService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test referencial actualizado exitosamente");
		body.put("test", test);

		return ResponseEntity.ok(body);
	}


	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Map<String, Object>> deactivate(@PathVariable Long id) {

		testService.deactivate(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test referencial desactivado exitosamente");

		return ResponseEntity.ok(body);
	}


	@PatchMapping("/{id}/activate")
	public ResponseEntity<Map<String, Object>> activate(@PathVariable Long id) {

		testService.activate(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test referencial activado exitosamente");

		return ResponseEntity.ok(body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		testService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Test referencial borrado exitosamente");

		return ResponseEntity.ok(body);
	}
	

}
