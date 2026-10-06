package com.proemsalud.laboratory.doctor.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.doctor.dto.create.CreateDoctorRequest;
import com.proemsalud.laboratory.doctor.dto.response.DoctorResponse;

@Component
public class DoctorMapper {
	
	public Doctor toEntity(CreateDoctorRequest request) {
		return Doctor.builder()
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.title(request.getTitle())
			.build();
	}

	public DoctorResponse toResponse(Doctor doctor) {
		return DoctorResponse.builder()
			.id(doctor.getId())
			.firstName(doctor.getFirstName())
			.lastName(doctor.getLastName())
			.fullName(buildFullName(doctor))
			.title(doctor.getTitle())
			.createdAt(doctor.getCreatedAt())
			.updatedAt(doctor.getUpdatedAt())
			.build();
	}
	
	private String buildFullName(Doctor doctor) {
	    String first = doctor.getFirstName() != null ? doctor.getFirstName() : "";
	    String last = doctor.getLastName() != null ? doctor.getLastName() : "";
	    return (first + " " + last).trim();
	}
}
