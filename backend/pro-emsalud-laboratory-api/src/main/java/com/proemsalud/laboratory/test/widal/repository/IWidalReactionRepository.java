package com.proemsalud.laboratory.test.widal.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.test.widal.domain.WidalReaction;

public interface IWidalReactionRepository  extends JpaRepository<WidalReaction, Long>, JpaSpecificationExecutor<WidalReaction> {
	
	Optional<WidalReaction> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<WidalReaction> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<WidalReaction> findByOrderPatientId(Long patientId, Pageable pageable);
}
