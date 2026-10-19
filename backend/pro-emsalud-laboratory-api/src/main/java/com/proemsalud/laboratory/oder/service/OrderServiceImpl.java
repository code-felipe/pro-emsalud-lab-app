package com.proemsalud.laboratory.oder.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.doctor.repository.IDoctorRepository;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.dto.create.CreateOrderRequest;
import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;
import com.proemsalud.laboratory.oder.dto.response.PendingOrderNotificationResponse;
import com.proemsalud.laboratory.oder.dto.update.UpdateOrderRequest;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;
import com.proemsalud.laboratory.oder.filter.OrderFilter;
import com.proemsalud.laboratory.oder.mapper.OrderMapper;
import com.proemsalud.laboratory.oder.repository.IOrderRepository;
import com.proemsalud.laboratory.oder.specification.OrderSpecification;
import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.patient.repository.IPatientRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IDoctorRepository doctorRepository;

	@Autowired
	private IPatientRepository patientRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public OrderResponse create(CreateOrderRequest request, Long patientId) {

		Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(
				() -> new ResourceNotFoundException("El doctor con id: " + request.getDoctorId() + " no existe"));

		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("El paciente con id: " + patientId + " no existe"));

		Order order = orderMapper.toEntity(request, doctor, patient);
		order.setStatus(OrderStatus.PENDING);
		Order saved = orderRepository.save(order);

		return orderMapper.toResponse(saved);
	}

	@Override
	public OrderResponse update(Long id, UpdateOrderRequest request) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + id + " no existe "));

		Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(
				() -> new ResourceNotFoundException("El doctor con id: " + request.getDoctorId() + " no existe"));

		order.setDoctor(doctor);

		Order updatedOrder = orderRepository.save(order);

		return orderMapper.toResponse(updatedOrder);
	}

	@Override
	public OrderResponse findById(Long id) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + id + " no existe"));

		return orderMapper.toResponse(order);
	}

	@Override
	public Page<OrderResponse> findAll(OrderFilter filter, Pageable pageable, Long patientId) {

		Specification<Order> spec = OrderSpecification.withFilters(filter, patientId);

		return orderRepository.findAll(spec, pageable).map(orderMapper::toResponse);
	}

	@Override
	public void delete(Long id) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + id + " no existe"));

		orderRepository.delete(order);
	}

	@Override
	public void inProgress(Long id) {
		
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + id + " no existe" ));
		
		order.setStatus(OrderStatus.IN_PROGRESS);
		
		orderRepository.save(order);
		
		
	}

	@Override
	public void completed(Long id) {
		
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + id + " no existe"));
		
		order.setStatus(OrderStatus.COMPLETED);
		
		orderRepository.save(order);
		
	}

	@Override
	public OrderReportResponse getReport(Long orderId, Long patientId) {
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"La orden con id: " + orderId + " no existe" ));
		
	    if (!order.getPatient().getId().equals(patientId)) {
	        throw new ResourceNotFoundException(
	                "La orden no pertenece al paciente indicado");
	    }

	    return orderMapper.toReport(order);
		
	}
	
	/*
	 * NOTIFICATIONS
	 */

	@Transactional(readOnly = true)
	public Long countPendingOrders() {
	    return orderRepository.countByStatusIn(
	        List.of(
	            OrderStatus.PENDING,
	            OrderStatus.IN_PROGRESS
	        )
	    );
	}
	
	@Override
	public List<PendingOrderNotificationResponse> getPendingOrderNotifications() {
	    return orderRepository.findOrderNotifications(
	            List.of(OrderStatus.PENDING, OrderStatus.IN_PROGRESS));
	}
}
