package com.proemsalud.laboratory.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.patient.dto.create.CreatePatientRequest;
import com.proemsalud.laboratory.patient.dto.response.PatientResponse;
import com.proemsalud.laboratory.patient.dto.update.UpdatePatientRequest;
import com.proemsalud.laboratory.patient.filter.PatientFilter;
import com.proemsalud.laboratory.patient.mapper.PatientMapper;
import com.proemsalud.laboratory.patient.repository.IPatientRepository;
import com.proemsalud.laboratory.patient.specification.PatientSpecification;
import com.proemsalud.laboratory.patient.util.PatientCodeGenerator;

@Service
public class PatientServiceImpl implements IPatientService {
	
	@Autowired
	private IPatientRepository patientRepository;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private PatientCodeGenerator patientCode;
	
	@Override
	@Transactional
	public PatientResponse create(CreatePatientRequest request) {
		

	    Patient patient = patientMapper.toEntity(request);
	    
	    patient.setPrimaryPhoneNumber(request.getPrimaryPhoneNumber());
	    
	    if(request.getOptionalPhoneNumber() != null) {
	    	patient.setOptionalPhoneNumber(request.getOptionalPhoneNumber());
	    }

	    Patient saved = patientRepository.save(patient);

	    String code = patientCode.generate(saved.getId());
	    saved.setCode(code);

	    return patientMapper.toResponse(saved);
	}

	@Override
	@Transactional
	public PatientResponse update(Long id, UpdatePatientRequest request) {
		
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El paciente no existe con id: " + id));
	
		patient.setGender(request.getGender());
		patient.setFirstName(request.getFirstName());
		patient.setMiddleName(request.getMiddleName());
		patient.setFatherLastName(request.getFatherLastName());
		patient.setMotherLastName(request.getMotherLastName());
		patient.setDateOfBirth(request.getDateOfBirth());
		patient.setPrimaryPhoneNumber(request.getPrimaryPhoneNumber());
		patient.setOptionalPhoneNumber(request.getOptionalPhoneNumber());
		patient.setCity(request.getCity());
		
		 patient.setPrimaryPhoneNumber(request.getPrimaryPhoneNumber());
		 
		 if(request.getOptionalPhoneNumber() != null) {
			 
			 patient.setOptionalPhoneNumber(request.getOptionalPhoneNumber());
		    }

		

		return patientMapper.toResponse(patient);
	}

	@Override
	public PatientResponse findById(Long id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El paciente no existe con id: " + id));

		return patientMapper.toResponse(patient);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Page<PatientResponse> findAll(PatientFilter filter, Pageable pageable) {
		
		Specification<Patient> spec = PatientSpecification.withFilters(filter);

		Page<Patient> page = patientRepository.findAll(spec, pageable);

		return page.map(patientMapper::toResponse);
	}
	
	private String phoneNumberFormat(String number) {
		return "+502" + number; 	    
	}
	
	@Override
	public void delete(Long id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El paciente no existe con id: " + id));
		patientRepository.delete(patient);
		
	}
}
