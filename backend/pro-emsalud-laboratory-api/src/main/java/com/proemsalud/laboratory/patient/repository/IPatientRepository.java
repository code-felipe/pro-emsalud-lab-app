package com.proemsalud.laboratory.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.proemsalud.laboratory.patient.domain.Patient;

public interface IPatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

	@Query("""
			    SELECT COUNT(p.id)
			    FROM Patient p
			""")
	public Integer totalPatients();
}
