package com.proemsalud.laboratory.test.torch.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.torch.domain.Torch;

public interface ITorchRepository extends JpaRepository<Torch, Long>, JpaSpecificationExecutor<Torch> {
	
	Optional<Torch> findByIdAndOrderId(Long id, Long orderId);
	
	boolean existsByTestId(Long testId);
	
	  Optional<Torch> findByIdAndOrderIdAndOrderPatientId(
	            Long id,
	            Long orderId,
	            Long patientId
	    );
	  
	  Page<Torch> findByOrderPatientId(Long patientId, Pageable pageable);
}
