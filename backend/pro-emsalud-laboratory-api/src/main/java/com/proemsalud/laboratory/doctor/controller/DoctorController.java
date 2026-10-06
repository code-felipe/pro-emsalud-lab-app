package com.proemsalud.laboratory.doctor.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
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
import com.proemsalud.laboratory.doctor.dto.create.CreateDoctorRequest;
import com.proemsalud.laboratory.doctor.dto.response.DoctorResponse;
import com.proemsalud.laboratory.doctor.dto.update.UpdateDoctorRequest;
import com.proemsalud.laboratory.doctor.filter.DoctorFilter;
import com.proemsalud.laboratory.doctor.service.IDoctorService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	
	@Autowired
	private IDoctorService doctorService;

	
	@GetMapping("/{id}")
	public ResponseEntity<DoctorResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(doctorService.findById(id));
	}


	@GetMapping
	public ResponseEntity<Page<DoctorResponse>> list(DoctorFilter filter,
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(doctorService.findAll(filter, pageable));
	}


	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateDoctorRequest request) {

		DoctorResponse doctor = doctorService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Ficha del Doctor creada exitosamente");
		body.put("doctor", doctor);

		return ResponseEntity.created(URI.create("/api/doctors/" + doctor.getId())).body(body);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateDoctorRequest request) {

		DoctorResponse doctor = doctorService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Ficha del Doctor actualizada exitosamente");
		body.put("doctor", doctor);

		return ResponseEntity.ok(body);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		doctorService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Ficha del Doctor borrada exitosamente");

		return ResponseEntity.ok(body);
	}
}
