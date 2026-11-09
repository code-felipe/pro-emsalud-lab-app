package com.proemsalud.laboratory.test.urinalysis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.urinalysis.dto.create.CreateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisResponse;
import com.proemsalud.laboratory.test.urinalysis.dto.update.UpdateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.filter.UrinalysisFilter;

public interface IUrinalysisService {
	
	UrinalysisResponse create(CreateUrinalysisRequest request, Long orderId, Long patientId);
	
	Page<UrinalysisResponse> findAll(UrinalysisFilter filter, Pageable pageable, Long orderId, Long patientId);

	UrinalysisResponse update(Long id, UpdateUrinalysisRequest request);

	UrinalysisResponse findById(Long id);
	
	UrinalysisResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<UrinalysisResponse>findByPatientId(UrinalysisFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
}
