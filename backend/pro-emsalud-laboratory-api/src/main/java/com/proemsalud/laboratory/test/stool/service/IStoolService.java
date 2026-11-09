package com.proemsalud.laboratory.test.stool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.stool.dto.create.CreateStoolRequest;
import com.proemsalud.laboratory.test.stool.dto.response.StoolResponse;
import com.proemsalud.laboratory.test.stool.dto.update.UpdateStoolRequest;
import com.proemsalud.laboratory.test.stool.filter.StoolFilter;
import com.proemsalud.laboratory.test.urinalysis.dto.create.CreateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisResponse;
import com.proemsalud.laboratory.test.urinalysis.dto.update.UpdateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.filter.UrinalysisFilter;

public interface IStoolService {
	
	StoolResponse create(CreateStoolRequest request, Long orderId, Long patientId);
	
	Page<StoolResponse> findAll(StoolFilter filter, Pageable pageable, Long orderId, Long patientId);

	StoolResponse update(Long id, UpdateStoolRequest request);

	StoolResponse findById(Long id);
	
	StoolResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<StoolResponse>findByPatientId(StoolFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
}
