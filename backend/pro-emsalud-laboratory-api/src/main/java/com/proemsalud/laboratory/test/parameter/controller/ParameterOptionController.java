package com.proemsalud.laboratory.test.parameter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterOptionResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.service.IParameterOptionService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/parameter-options")
public class ParameterOptionController {

	@Autowired
	private IParameterOptionService optionService;

	@GetMapping
	public ResponseEntity<Page<ParameterOptionResponse>> findAll(Pageable pageable) {
		return ResponseEntity.ok(optionService.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParameterOptionResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(optionService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateParameterOptionRequest request) {

		ParameterOptionResponse option = optionService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Opción creada exitosamente");
		body.put("parameterOption", option);

		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateParameterOptionRequest request) {

		ParameterOptionResponse option = optionService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Opción actualizada exitosamente");
		body.put("parameterOption", option);

		return ResponseEntity.ok(body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		optionService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Opción borrada exitosamente");

		return ResponseEntity.ok(body);
	}
}
