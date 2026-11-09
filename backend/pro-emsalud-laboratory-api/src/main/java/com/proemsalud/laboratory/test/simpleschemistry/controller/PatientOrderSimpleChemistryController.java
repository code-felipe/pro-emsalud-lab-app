package com.proemsalud.laboratory.test.simpleschemistry.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.result.dto.create.CreateTestResultRequest;
import com.proemsalud.laboratory.result.dto.response.TestResultResponse;
import com.proemsalud.laboratory.result.filter.TestResultFilter;
import com.proemsalud.laboratory.result.service.ITestResultService;
import com.proemsalud.laboratory.test.simpleschemistry.dto.create.CreateSimpleChemistryRequest;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryResponse;
import com.proemsalud.laboratory.test.simpleschemistry.filter.SimpleChemistryFilter;
import com.proemsalud.laboratory.test.simpleschemistry.service.ISimpleChemistryService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients/{patientId}/orders/{orderId}/simple-chemistry")
public class PatientOrderSimpleChemistryController {
	
	@Autowired
	private ISimpleChemistryService testResultService;

	@GetMapping
	public ResponseEntity<Page<SimpleChemistryResponse>> list(
			SimpleChemistryFilter filter,
			@PageableDefault(page = 0, size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long patientId,
			@PathVariable Long orderId) {

		return ResponseEntity.ok(testResultService.findAll(filter, pageable, orderId, patientId));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateSimpleChemistryRequest request,
			@PathVariable Long patientId,
			@PathVariable Long orderId
			) {

		SimpleChemistryResponse result = testResultService.create(request, orderId, patientId);

		Map<String, Object> body = new HashMap<>();

		body.put("message", "Resultado creado exitosamente");
		body.put("result", result);

		return ResponseEntity.created(URI.create("/api/simple-chemistry/" + result.getId())).body(body);
	}
	
	@GetMapping("/{testResultId}")
	public ResponseEntity<SimpleChemistryResponse> getOne(@PathVariable Long patientId, @PathVariable Long orderId,
			@PathVariable Long testResultId) {

		return ResponseEntity.ok(testResultService.findByIdAndOrderIdAndPatientId(testResultId, orderId, patientId));
	}
}
