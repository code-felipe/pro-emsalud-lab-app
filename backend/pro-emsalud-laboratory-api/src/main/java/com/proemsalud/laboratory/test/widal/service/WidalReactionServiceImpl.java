package com.proemsalud.laboratory.test.widal.service;

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
import com.proemsalud.laboratory.test.widal.domain.WidalReaction;
import com.proemsalud.laboratory.test.widal.dto.create.CreateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionResponse;
import com.proemsalud.laboratory.test.widal.dto.update.UpdateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.filter.WidalReactionFilter;
import com.proemsalud.laboratory.test.widal.mapper.WidalReactionMapper;
import com.proemsalud.laboratory.test.widal.repository.IWidalReactionRepository;
import com.proemsalud.laboratory.test.widal.specification.WidalReactionSpecification;

import jakarta.transaction.Transactional;

@Service
public class WidalReactionServiceImpl implements IWidalReactionService {
	
	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IWidalReactionRepository testResultRepository;

	@Autowired
	private ITestRepository testRepository;

	@Autowired
	private WidalReactionMapper testResultMapper;
	
	@Transactional
	@Override
	public WidalReactionResponse create(CreateWidalReactionRequest request, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		WidalReaction widalReactionResult = testResultMapper.toEntity(request, order, test);

		WidalReaction saved = testResultRepository.save(widalReactionResult);
		
	    if (order.getStatus() == OrderStatus.PENDING) {
	        order.setStatus(OrderStatus.IN_PROGRESS);
	    }

		return testResultMapper.toResponse(saved);
	}

	@Override
	public Page<WidalReactionResponse> findAll(WidalReactionFilter filter, Pageable pageable, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Specification<WidalReaction> spec = WidalReactionSpecification.withFilters(filter, order.getId(), order.getPatient().getId());
		return testResultRepository.findAll(spec, pageable).map(testResultMapper::toResponse);
	}

	@Override
	public WidalReactionResponse update(Long id, UpdateWidalReactionRequest request) {

		WidalReaction testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		testResult.setWidalResult(request.getWidalResult());
		testResult.setTest(test);
		testResult.setUnitPrice(test.getPrice());

		WidalReaction updatedTestResult = testResultRepository.save(testResult);

		return testResultMapper.toResponse(updatedTestResult);
	}

	@Override
	public WidalReactionResponse findById(Long id) {

		WidalReaction testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public void delete(Long id) {
		WidalReaction testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		testResultRepository.delete(testResult);

	}

	@Override
	public WidalReactionResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId) {

		WidalReaction testResult = testResultRepository.findByIdAndOrderIdAndOrderPatientId(id, orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Resultado de test no encontrado"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public Page<WidalReactionResponse> findByPatientId(
			WidalReactionFilter filter,
	        Long patientId,
	        Pageable pageable) {
		Specification<WidalReaction> spec = WidalReactionSpecification.withFilters(filter, null, patientId);
		
		 return testResultRepository
		            .findAll(spec, pageable)
		            .map(testResultMapper::toResponse);
	}
}
