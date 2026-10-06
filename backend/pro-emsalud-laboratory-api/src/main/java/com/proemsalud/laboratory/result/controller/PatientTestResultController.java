package com.proemsalud.laboratory.result.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.result.dto.response.TestResultResponse;
import com.proemsalud.laboratory.result.filter.TestResultFilter;
import com.proemsalud.laboratory.result.service.ITestResultService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients/{patientId}/test-results")
public class PatientTestResultController {
	
	@Autowired
	private ITestResultService testResultService;
	
	@GetMapping
	public ResponseEntity<Page<TestResultResponse>> list(
			TestResultFilter filter,
			@PageableDefault(page = 0, size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long patientId) {

		return ResponseEntity.ok(testResultService.findByPatientId(filter, patientId, pageable));
	}
}
