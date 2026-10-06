package com.proemsalud.laboratory.doctor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.catalog.dto.response.TestCategoryResponse;
import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.doctor.dto.create.CreateDoctorRequest;
import com.proemsalud.laboratory.doctor.dto.response.DoctorResponse;
import com.proemsalud.laboratory.doctor.dto.update.UpdateDoctorRequest;
import com.proemsalud.laboratory.doctor.filter.DoctorFilter;
import com.proemsalud.laboratory.doctor.mapper.DoctorMapper;
import com.proemsalud.laboratory.doctor.repository.IDoctorRepository;
import com.proemsalud.laboratory.doctor.specification.DoctorSpecification;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.oder.repository.IOrderRepository;

@Service
public class DoctorServiceImpl implements IDoctorService {
	
	@Autowired
	private IDoctorRepository doctorRepository;
	
	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private DoctorMapper doctorMapper;

	@Override
	public DoctorResponse create(CreateDoctorRequest request) {
		Doctor doctor = doctorMapper.toEntity(request);
		Doctor saved = doctorRepository.save(doctor);
		
		
		return doctorMapper.toResponse(saved);
	}

	@Override
	public DoctorResponse update(Long id, UpdateDoctorRequest request) {
		
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El doctor con id: " + id + " no existe"));
		
		doctor.setFirstName(request.getFirstName());
		doctor.setLastName(request.getLastName());
		doctor.setTitle(request.getTitle());
		
		Doctor updated = doctorRepository.save(doctor);
		
		return doctorMapper.toResponse(updated);
	}

	@Override
	public DoctorResponse findById(Long id) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El doctor con id: " + id + " no existe"));
		
		return doctorMapper.toResponse(doctor);
	}

	@Override
	public Page<DoctorResponse> findAll(DoctorFilter filter, Pageable pageable) {
		return doctorRepository.findAll(DoctorSpecification.withFilters(filter), pageable)
				.map(doctorMapper::toResponse);
	}

	@Override
	public void delete(Long id) {
		
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El doctor con id: " + id + " no existe"));
		
		if (orderRepository.existsByDoctorId(id)) {
		        throw new IllegalArgumentException(
		            "No se puede eliminar la ficha del doctor porque está siendo utilizado en las ordenes de algun paciente."
		        );
		    }
		doctorRepository.delete(doctor);
		
	}

	@Override
	public List<DoctorResponse> search(String firstName) {

		String safeName = firstName != null ? firstName : "";

		return doctorRepository.findByFirstNameContainingIgnoreCase(safeName).stream()
				.map(doctorMapper::toResponse)
				.toList();
	}

}
