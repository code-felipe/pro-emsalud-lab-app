package com.proemsalud.laboratory.test.torch.service;

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
import com.proemsalud.laboratory.test.torch.domain.Torch;
import com.proemsalud.laboratory.test.torch.dto.create.CreateTorchRequest;
import com.proemsalud.laboratory.test.torch.dto.response.TorchResponse;
import com.proemsalud.laboratory.test.torch.dto.update.UpdateTorchRequest;
import com.proemsalud.laboratory.test.torch.filter.TorchFilter;
import com.proemsalud.laboratory.test.torch.mapper.TorchMapper;
import com.proemsalud.laboratory.test.torch.repository.ITorchRepository;
import com.proemsalud.laboratory.test.torch.specification.TorchSpecification;
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
public class TorchServiceImpl implements ITorchService {
	
	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private ITorchRepository testResultRepository;

	@Autowired
	private ITestRepository testRepository;

	@Autowired
	private TorchMapper testResultMapper;
	
	@Transactional
	@Override
	public TorchResponse create(CreateTorchRequest request, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		Torch torchResult = testResultMapper.toEntity(request, order, test);

		Torch saved = testResultRepository.save(torchResult);
		
	    if (order.getStatus() == OrderStatus.PENDING) {
	        order.setStatus(OrderStatus.IN_PROGRESS);
	    }

		return testResultMapper.toResponse(saved);
	}

	@Override
	public Page<TorchResponse> findAll(TorchFilter filter, Pageable pageable, Long orderId, Long patientId) {

		Order order = orderRepository.findByIdAndPatientId(orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no pertenece al paciente con id: " + patientId));

		Specification<Torch> spec = TorchSpecification.withFilters(filter, order.getId(), order.getPatient().getId());
		return testResultRepository.findAll(spec, pageable).map(testResultMapper::toResponse);
	}

	@Override
	public TorchResponse update(Long id, UpdateTorchRequest request) {

		Torch testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		Test test = testRepository.findById(request.getTestId()).orElseThrow(
				() -> new ResourceNotFoundException("El test con id: " + request.getTestId() + " no existe"));

		testResult.setTorchIgG(request.getTorchIgG());
		testResult.setTorchIgM(request.getTorchIgM());
		testResult.setTest(test);
		testResult.setUnitPrice(test.getPrice());

		Torch updatedTestResult = testResultRepository.save(testResult);

		return testResultMapper.toResponse(updatedTestResult);
	}

	@Override
	public TorchResponse findById(Long id) {

		Torch testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public void delete(Long id) {
		Torch testResult = testResultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El resultado con id: " + id + " no existe"));

		testResultRepository.delete(testResult);

	}

	@Override
	public TorchResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId) {

		Torch testResult = testResultRepository.findByIdAndOrderIdAndOrderPatientId(id, orderId, patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Resultado de test no encontrado"));

		return testResultMapper.toResponse(testResult);
	}

	@Override
	public Page<TorchResponse> findByPatientId(
			TorchFilter filter,
	        Long patientId,
	        Pageable pageable) {
		Specification<Torch> spec = TorchSpecification.withFilters(filter, null, patientId);
		
		 return testResultRepository
		            .findAll(spec, pageable)
		            .map(testResultMapper::toResponse);
	}
}
