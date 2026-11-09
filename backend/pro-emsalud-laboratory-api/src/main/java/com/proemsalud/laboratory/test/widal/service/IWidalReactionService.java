package com.proemsalud.laboratory.test.widal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.widal.dto.create.CreateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionResponse;
import com.proemsalud.laboratory.test.widal.dto.update.UpdateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.filter.WidalReactionFilter;

public interface IWidalReactionService {
	
	WidalReactionResponse create(CreateWidalReactionRequest request, Long orderId, Long patientId);
	
	Page<WidalReactionResponse> findAll(WidalReactionFilter filter, Pageable pageable, Long orderId, Long patientId);

	WidalReactionResponse update(Long id, UpdateWidalReactionRequest request);

	WidalReactionResponse findById(Long id);
	
	WidalReactionResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<WidalReactionResponse>findByPatientId(WidalReactionFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
}
