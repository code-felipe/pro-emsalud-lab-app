package com.proemsalud.laboratory.test.urinalysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.repository.ITestRepository;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;
import com.proemsalud.laboratory.oder.repository.IOrderRepository;
import com.proemsalud.laboratory.test.urinalysis.domain.Urinalysis;
import com.proemsalud.laboratory.test.urinalysis.dto.create.CreateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisResponse;
import com.proemsalud.laboratory.test.urinalysis.dto.update.UpdateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.filter.UrinalysisFilter;
import com.proemsalud.laboratory.test.urinalysis.mapper.UrinalysisMapper;
import com.proemsalud.laboratory.test.urinalysis.repository.IUrinalysisRepository;
import com.proemsalud.laboratory.test.urinalysis.specification.UrinalysisSpecification;


import jakarta.transaction.Transactional;

@Service
public class UrinalysisServiceImpl implements IUrinalysisService {
	
	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IUrinalysisRepository testResultRepository;

	@Autowired
	private ITestRepository testRepository;

	@Autowired
	private UrinalysisMapper testResultMapper;
	
	@Transactional
	@Override
	public UrinalysisResponse create(CreateUrinalysisRequest request, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		Urinalysis urinalysisResult = testResultMapper.toEntity(request, order, test);

		Urinalysis saved = testResultRepository.save(urinalysisResult);
		
	    if (order.getStatus() == OrderStatus.PENDING) {
	        order.setStatus(OrderStatus.IN_PROGRESS);
	    }

		return testResultMapper.toResponse(saved);
	}

	@Override
	public Page<UrinalysisResponse> findAll(UrinalysisFilter filter, Pageable pageable, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Specification<Urinalysis> spec = UrinalysisSpecification.withFilters(filter, order.getId(), order.getPatient().getId());
		return testResultRepository.findAll(spec, pageable).map(testResultMapper::toResponse);
	}

	@Override
	public UrinalysisResponse update(Long id, UpdateUrinalysisRequest request) {

		Urinalysis testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		testResult.setUrinalysisResult(request.getUrinalysisResult());
		testResult.setTest(test);
		testResult.setUnitPrice(test.getPrice());

		Urinalysis updatedTestResult = testResultRepository.save(testResult);

		return testResultMapper.toResponse(updatedTestResult);
	}

	@Override
	public UrinalysisResponse findById(Long id) {

		Urinalysis testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public void delete(Long id) {
		Urinalysis testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		testResultRepository.delete(testResult);

	}

	@Override
	public UrinalysisResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId) {

		Urinalysis testResult = testResultRepository.findByIdAndOrderIdAndOrderPatientId(id, orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Resultado de test no encontrado"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public Page<UrinalysisResponse> findByPatientId(
			UrinalysisFilter filter,
	        Long patientId,
	        Pageable pageable) {
		Specification<Urinalysis> spec = UrinalysisSpecification.withFilters(filter, null, patientId);
		
		 return testResultRepository
		            .findAll(spec, pageable)
		            .map(testResultMapper::toResponse);
	}
}
