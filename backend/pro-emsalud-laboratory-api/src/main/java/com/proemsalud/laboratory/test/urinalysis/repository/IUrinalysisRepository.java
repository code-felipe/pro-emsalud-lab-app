package com.proemsalud.laboratory.test.urinalysis.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.test.urinalysis.domain.Urinalysis;

public interface IUrinalysisRepository extends JpaRepository<Urinalysis, Long>, JpaSpecificationExecutor<Urinalysis> {
	
	Optional<Urinalysis> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<Urinalysis> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<Urinalysis> findByOrderPatientId(Long patientId, Pageable pageable);
}
