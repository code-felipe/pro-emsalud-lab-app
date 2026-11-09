package com.proemsalud.laboratory.test.stool.service;

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
import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.stool.dto.create.CreateStoolRequest;
import com.proemsalud.laboratory.test.stool.dto.response.StoolResponse;
import com.proemsalud.laboratory.test.stool.dto.update.UpdateStoolRequest;
import com.proemsalud.laboratory.test.stool.filter.StoolFilter;
import com.proemsalud.laboratory.test.stool.mapper.StoolMapper;
import com.proemsalud.laboratory.test.stool.repository.IStoolRepository;
import com.proemsalud.laboratory.test.stool.specification.StoolSpecification;
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
public class StoolServiceImpl implements IStoolService {
	
	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IStoolRepository testResultRepository;

	@Autowired
	private ITestRepository testRepository;

	@Autowired
	private StoolMapper testResultMapper;
	
	@Transactional
	@Override
	public StoolResponse create(CreateStoolRequest request, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		Stool stoolResult = testResultMapper.toEntity(request, order, test);

		Stool saved = testResultRepository.save(stoolResult);
		
	    if (order.getStatus() == OrderStatus.PENDING) {
	        order.setStatus(OrderStatus.IN_PROGRESS);
	    }

		return testResultMapper.toResponse(saved);
	}

	@Override
	public Page<StoolResponse> findAll(StoolFilter filter, Pageable pageable, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Specification<Stool> spec = StoolSpecification.withFilters(filter, order.getId(), order.getPatient().getId());
		return testResultRepository.findAll(spec, pageable).map(testResultMapper::toResponse);
	}

	@Override
	public StoolResponse update(Long id, UpdateStoolRequest request) {

		Stool testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		testResult.setStoolResult(request.getStoolResult());
		testResult.setTest(test);
		testResult.setUnitPrice(test.getPrice());

		Stool updatedTestResult = testResultRepository.save(testResult);

		return testResultMapper.toResponse(updatedTestResult);
	}

	@Override
	public StoolResponse findById(Long id) {

		Stool testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public void delete(Long id) {
		Stool testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		testResultRepository.delete(testResult);

	}

	@Override
	public StoolResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId) {

		Stool testResult = testResultRepository.findByIdAndOrderIdAndOrderPatientId(id, orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Resultado de test no encontrado"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public Page<StoolResponse> findByPatientId(
			StoolFilter filter,
	        Long patientId,
	        Pageable pageable) {
		Specification<Stool> spec = StoolSpecification.withFilters(filter, null, patientId);
		
		 return testResultRepository
		            .findAll(spec, pageable)
		            .map(testResultMapper::toResponse);
	}
}
