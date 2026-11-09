package com.proemsalud.laboratory.test.stool.controller;

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

import com.proemsalud.laboratory.test.stool.dto.response.StoolResponse;
import com.proemsalud.laboratory.test.stool.dto.update.UpdateStoolRequest;
import com.proemsalud.laboratory.test.stool.service.IStoolService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/stools")
public class StoolController {
	
	@Autowired
	private IStoolService testResultService;

	@GetMapping("/{id}")
	public ResponseEntity<StoolResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(testResultService.findById(id));
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateStoolRequest request) {

		StoolResponse testResult = testResultService.update(id, request);

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
