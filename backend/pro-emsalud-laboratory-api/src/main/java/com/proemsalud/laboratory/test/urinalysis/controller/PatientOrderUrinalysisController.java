package com.proemsalud.laboratory.test.urinalysis.controller;

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

import com.proemsalud.laboratory.test.urinalysis.dto.create.CreateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisResponse;
import com.proemsalud.laboratory.test.urinalysis.filter.UrinalysisFilter;
import com.proemsalud.laboratory.test.urinalysis.service.IUrinalysisService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients/{patientId}/orders/{orderId}/urinalysis")
public class PatientOrderUrinalysisController {
	
	@Autowired
	private IUrinalysisService testResultService;

	@GetMapping
	public ResponseEntity<Page<UrinalysisResponse>> list(
			UrinalysisFilter filter,
			@PageableDefault(page = 0, size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long patientId,
			@PathVariable Long orderId) {

		return ResponseEntity.ok(testResultService.findAll(filter, pageable, orderId, patientId));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateUrinalysisRequest request,
			@PathVariable Long patientId,
			@PathVariable Long orderId
			) {

		UrinalysisResponse result = testResultService.create(request, orderId, patientId);

		Map<String, Object> body = new HashMap<>();

		body.put("message", "Resultado creado exitosamente");
		body.put("result", result);

		return ResponseEntity.created(URI.create("/api/urinalysis/" + result.getId())).body(body);
	}
	
	@GetMapping("/{testResultId}")
	public ResponseEntity<UrinalysisResponse> getOne(@PathVariable Long patientId, @PathVariable Long orderId,
			@PathVariable Long testResultId) {

		return ResponseEntity.ok(testResultService.findByIdAndOrderIdAndPatientId(testResultId, orderId, patientId));
	}
}
