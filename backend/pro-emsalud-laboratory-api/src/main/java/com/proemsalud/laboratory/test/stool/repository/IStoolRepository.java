package com.proemsalud.laboratory.test.stool.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.test.stool.domain.Stool;

public interface IStoolRepository extends JpaRepository<Stool, Long>, JpaSpecificationExecutor<Stool> {
	
	Optional<Stool> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<Stool> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<Stool> findByOrderPatientId(Long patientId, Pageable pageable);
}
