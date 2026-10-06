package com.proemsalud.laboratory.patient.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.patient.dto.create.CreatePatientRequest;
import com.proemsalud.laboratory.patient.dto.response.PatientResponse;
import com.proemsalud.laboratory.patient.dto.update.UpdatePatientRequest;
import com.proemsalud.laboratory.patient.filter.PatientFilter;

public interface IPatientService {
	
	PatientResponse create(CreatePatientRequest request);

	PatientResponse update(Long id, UpdatePatientRequest request);

	PatientResponse findById(Long id);

	Page<PatientResponse> findAll(PatientFilter filter, Pageable pageable);
	
	void delete(Long id);
	
}
