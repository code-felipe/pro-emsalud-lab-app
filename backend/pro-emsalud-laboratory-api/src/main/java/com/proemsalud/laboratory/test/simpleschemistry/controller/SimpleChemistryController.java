package com.proemsalud.laboratory.test.simpleschemistry.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryResponse;
import com.proemsalud.laboratory.test.simpleschemistry.dto.update.UpdateSimpleChemistryRequest;
import com.proemsalud.laboratory.test.simpleschemistry.service.ISimpleChemistryService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/simple-chemistry")
public class SimpleChemistryController {
	
	@Autowired
	private ISimpleChemistryService testResultService;

	@GetMapping("/{id}")
	public ResponseEntity<SimpleChemistryResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(testResultService.findById(id));
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateSimpleChemistryRequest request) {

		SimpleChemistryResponse testResult = testResultService.update(id, request);

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
