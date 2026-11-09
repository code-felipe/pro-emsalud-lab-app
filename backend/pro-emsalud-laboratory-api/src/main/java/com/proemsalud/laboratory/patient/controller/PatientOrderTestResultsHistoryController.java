package com.proemsalud.laboratory.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.patient.dto.response.AllTestResultsHistory;
import com.proemsalud.laboratory.patient.service.IPatientService;
import org.springframework.data.domain.Sort;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients/{patientId}/results")
public class PatientOrderTestResultsHistoryController {
	
	@Autowired
	private IPatientService patientService;
	
    @GetMapping
	public ResponseEntity<Page<AllTestResultsHistory>> findAllTestResultsByPatientAndOrder(
	        @PathVariable Long patientId,
	        @PageableDefault(
	            page = 0,
	            size = 10,
	            sort = "orderCreatedAt",
	            direction = Sort.Direction.DESC
	        ) Pageable pageable
	) {
	    return ResponseEntity.ok(
	    		patientService.findAllTestResultsHistory(
	            patientId,
	            pageable
	        )
	    );
	}
}
