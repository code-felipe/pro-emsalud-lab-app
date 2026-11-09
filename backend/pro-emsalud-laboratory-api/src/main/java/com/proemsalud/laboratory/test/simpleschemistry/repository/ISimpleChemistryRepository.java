package com.proemsalud.laboratory.test.simpleschemistry.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.proemsalud.laboratory.test.simpleschemistry.domain.SimpleChemistry;

public interface ISimpleChemistryRepository extends JpaRepository<SimpleChemistry, Long>, JpaSpecificationExecutor<SimpleChemistry> {
	
	Optional<SimpleChemistry> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<SimpleChemistry> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<SimpleChemistry> findByOrderPatientId(Long patientId, Pageable pageable);

}
