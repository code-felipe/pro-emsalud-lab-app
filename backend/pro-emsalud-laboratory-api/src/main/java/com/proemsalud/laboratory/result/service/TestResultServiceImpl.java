package com.proemsalud.laboratory.result.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.repository.ITestRepository;
import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;
import com.proemsalud.laboratory.oder.repository.IOrderRepository;
import com.proemsalud.laboratory.oder.specification.OrderSpecification;
import com.proemsalud.laboratory.result.domain.TestResult;
import com.proemsalud.laboratory.result.dto.create.CreateTestResultRequest;
import com.proemsalud.laboratory.result.dto.response.TestResultResponse;
import com.proemsalud.laboratory.result.dto.update.UpdateTestResultRequest;
import com.proemsalud.laboratory.result.filter.TestResultFilter;
import com.proemsalud.laboratory.result.mapper.TestResultMapper;
import com.proemsalud.laboratory.result.repository.ITestResultRepository;
import com.proemsalud.laboratory.result.specification.TestResultSpecification;

import jakarta.transaction.Transactional;

@Service
public class TestResultServiceImpl implements ITestResultService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private ITestResultRepository testResultRepository;

	@Autowired
	private ITestRepository testRepository;

	@Autowired
	private TestResultMapper testResultMapper;
	
	@Transactional
	@Override
	public TestResultResponse create(CreateTestResultRequest request, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		TestResult testResult = testResultMapper.toEntity(request, order, test);

		TestResult saved = testResultRepository.save(testResult);
		
	    if (order.getStatus() == OrderStatus.PENDING) {
	        order.setStatus(OrderStatus.IN_PROGRESS);
	    }

		return testResultMapper.toResponse(saved);
	}

	@Override
	public Page<TestResultResponse> findAll(TestResultFilter filter, Pageable pageable, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Specification<TestResult> spec = TestResultSpecification.withFilters(filter, order.getId(), order.getPatient().getId());
		return testResultRepository.findAll(spec, pageable).map(testResultMapper::toResponse);
	}

	@Override
	public TestResultResponse update(Long id, UpdateTestResultRequest request) {

		TestResult testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		testResult.setResult(request.getResult());
		testResult.setTest(test);
		testResult.setUnitPrice(test.getPrice());

		TestResult updatedTestResult = testResultRepository.save(testResult);

		return testResultMapper.toResponse(updatedTestResult);
	}

	@Override
	public TestResultResponse findById(Long id) {

		TestResult testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public void delete(Long id) {
		TestResult testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		testResultRepository.delete(testResult);

	}

	@Override
	public TestResultResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId) {

		TestResult testResult = testResultRepository.findByIdAndOrderIdAndOrderPatientId(id, orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Resultado de test no encontrado"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public Page<TestResultResponse> findByPatientId(
			TestResultFilter filter,
	        Long patientId,
	        Pageable pageable) {
		Specification<TestResult> spec = TestResultSpecification.withFilters(filter, null, patientId);
		
		 return testResultRepository
		            .findAll(spec, pageable)
		            .map(testResultMapper::toResponse);
	}


}
