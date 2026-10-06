package com.proemsalud.laboratory.doctor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.doctor.dto.create.CreateDoctorRequest;
import com.proemsalud.laboratory.doctor.dto.response.DoctorResponse;
import com.proemsalud.laboratory.doctor.dto.update.UpdateDoctorRequest;
import com.proemsalud.laboratory.doctor.filter.DoctorFilter;

public interface IDoctorService {
	
	DoctorResponse create(CreateDoctorRequest request);

	DoctorResponse update(Long id, UpdateDoctorRequest request);

	DoctorResponse findById(Long id);

	Page<DoctorResponse> findAll(DoctorFilter filter, Pageable pageable);
	
	List<DoctorResponse> search(String firstName);
	
	void delete(Long id);
}
