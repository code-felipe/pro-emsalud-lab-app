package com.proemsalud.laboratory.result.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proemsalud.laboratory.result.dto.response.TestResultResponse;
import com.proemsalud.laboratory.result.dto.update.UpdateTestResultRequest;
import com.proemsalud.laboratory.result.service.ITestResultService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/test-results")
public class TestResultController {

	@Autowired
	private ITestResultService testResultService;

	@GetMapping("/{id}")
	public ResponseEntity<TestResultResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(testResultService.findById(id));
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateTestResultRequest request) {

		TestResultResponse testResult = testResultService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "El resultado ha sido actualizado exitosamente");
		body.put("testResult", testResult);

		return ResponseEntity.ok(body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		testResultService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Resultado borrado exitosamente");

		return ResponseEntity.ok(body);
	}
}
