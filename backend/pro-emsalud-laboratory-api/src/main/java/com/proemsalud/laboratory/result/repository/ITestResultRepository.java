package com.proemsalud.laboratory.result.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.result.domain.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITestResultRepository extends JpaRepository<TestResult, Long>, JpaSpecificationExecutor<TestResult> {
	
	Optional<TestResult> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<TestResult> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<TestResult> findByOrderPatientId(Long patientId, Pageable pageable);

		


}
