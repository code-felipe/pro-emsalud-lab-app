package com.proemsalud.laboratory.patient.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proemsalud.laboratory.patient.dto.create.CreatePatientRequest;
import com.proemsalud.laboratory.patient.dto.response.PatientResponse;
import com.proemsalud.laboratory.patient.dto.update.UpdatePatientRequest;
import com.proemsalud.laboratory.patient.filter.PatientFilter;
import com.proemsalud.laboratory.patient.service.IPatientService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private IPatientService patientService;
	
	@GetMapping
	public ResponseEntity<Page<PatientResponse>> list(PatientFilter filter,
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(patientService.findAll(filter, pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(patientService.findById(id));
	}
	

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreatePatientRequest request) {

		PatientResponse patient = patientService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Paciente creado exitosamente");
		body.put("patient", patient);

		return ResponseEntity.created(URI.create("/api/patients/" + patient.getId())).body(body);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdatePatientRequest request) {

		PatientResponse patient = patientService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Paciente actualizado exitosamente");
		body.put("patient", patient);

		return ResponseEntity.ok(body);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		patientService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Paciente borrado exitosamente");

		return ResponseEntity.ok(body);
	}


}
