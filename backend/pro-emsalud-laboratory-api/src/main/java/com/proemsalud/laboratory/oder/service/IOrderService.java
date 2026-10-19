package com.proemsalud.laboratory.oder.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.oder.dto.create.CreateOrderRequest;
import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;
import com.proemsalud.laboratory.oder.dto.response.PendingOrderNotificationResponse;
import com.proemsalud.laboratory.oder.dto.update.UpdateOrderRequest;
import com.proemsalud.laboratory.oder.filter.OrderFilter;

public interface IOrderService {
	
	OrderResponse create(CreateOrderRequest request, Long patientId);
	
	Page<OrderResponse> findAll(OrderFilter filter, Pageable pageable, Long patientId);

	OrderResponse update(Long id, UpdateOrderRequest request);

	OrderResponse findById(Long id);
	
	OrderReportResponse getReport(Long orderId, Long patientId);
	
	Long countPendingOrders();
	
	List<PendingOrderNotificationResponse>getPendingOrderNotifications();
	
	void inProgress(Long id);
	
	void completed(Long id);
	
	void delete(Long id);
	
	
}
