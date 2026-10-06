package com.proemsalud.laboratory.patient.mapper;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;


import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.patient.dto.create.CreatePatientRequest;
import com.proemsalud.laboratory.patient.dto.response.PatientResponse;

@Component
public class PatientMapper {
	
	public Patient toEntity(CreatePatientRequest request) {
		return Patient.builder()
			.gender(request.getGender())
			.firstName(request.getFirstName())
			.middleName(request.getMiddleName())
			.fatherLastName(request.getFatherLastName())
			.motherLastName(request.getMotherLastName())
			.dateOfBirth(request.getDateOfBirth())
			.primaryPhoneNumber(request.getPrimaryPhoneNumber())
			.optionalPhoneNumber(request.getOptionalPhoneNumber())
			.city(request.getCity())
			.build();
			
	}
	

	public PatientResponse toResponse(Patient patient) {
		return PatientResponse.builder()
			.id(patient.getId())
			.code(patient.getCode())
			.gender(patient.getGender())
			.firstName(patient.getFirstName())
			.middleName(patient.getMiddleName())
			.fatherLastName(patient.getFatherLastName())
			.motherLastName(patient.getMotherLastName())
			.dateOfBirth(patient.getDateOfBirth())
			.age(patient.calculateAgeDisplay())
			.primaryPhoneNumber(patient.getPrimaryPhoneNumber())
			.optionalPhoneNumber(patient.getOptionalPhoneNumber())
			.city(patient.getCity())
			.createdAt(patient.getCreatedAt())
			.updatedAt(patient.getUpdatedAt())
			.build();
	}
	
	private String normalizeBlank(String value) {
		return (value == null || value.isBlank()) ? null : value;
	}
	
}
