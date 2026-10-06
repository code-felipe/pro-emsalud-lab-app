package com.proemsalud.laboratory.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.doctor.domain.Doctor;

public interface IDoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor>{
	
	List<Doctor> findByFirstNameContainingIgnoreCase(String firstName);
}
