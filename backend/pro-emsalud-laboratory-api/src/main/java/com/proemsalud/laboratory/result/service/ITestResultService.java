package com.proemsalud.laboratory.result.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.result.dto.create.CreateTestResultRequest;
import com.proemsalud.laboratory.result.dto.response.TestResultResponse;
import com.proemsalud.laboratory.result.dto.update.UpdateTestResultRequest;
import com.proemsalud.laboratory.result.filter.TestResultFilter;

public interface ITestResultService {
	
	TestResultResponse create(CreateTestResultRequest request, Long orderId, Long patientId);
	
	Page<TestResultResponse> findAll(TestResultFilter filter, Pageable pageable, Long orderId, Long patientId);

	TestResultResponse update(Long id, UpdateTestResultRequest request);

	TestResultResponse findById(Long id);
	
	TestResultResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<TestResultResponse>findByPatientId(TestResultFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
	
}
