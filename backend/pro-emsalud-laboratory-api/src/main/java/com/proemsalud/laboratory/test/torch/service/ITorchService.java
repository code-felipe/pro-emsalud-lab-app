package com.proemsalud.laboratory.test.torch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.torch.dto.create.CreateTorchRequest;
import com.proemsalud.laboratory.test.torch.dto.response.TorchResponse;
import com.proemsalud.laboratory.test.torch.dto.update.UpdateTorchRequest;
import com.proemsalud.laboratory.test.torch.filter.TorchFilter;

public interface ITorchService {
	
	TorchResponse create(CreateTorchRequest request, Long orderId, Long patientId);
	
	Page<TorchResponse> findAll(TorchFilter filter, Pageable pageable, Long orderId, Long patientId);

	TorchResponse update(Long id, UpdateTorchRequest request);

	TorchResponse findById(Long id);
	
	TorchResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<TorchResponse>findByPatientId(TorchFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
}
