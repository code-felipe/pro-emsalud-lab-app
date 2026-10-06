package com.proemsalud.laboratory.oder.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import com.proemsalud.laboratory.oder.dto.create.CreateOrderRequest;
import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;
import com.proemsalud.laboratory.oder.filter.OrderFilter;
import com.proemsalud.laboratory.oder.service.IOrderService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/patients/{patientId}/orders")
public class PatientOrderController {

	@Autowired
	private IOrderService orderService;

	@GetMapping
	public ResponseEntity<Page<OrderResponse>> list(OrderFilter filter,
			@PageableDefault(page = 0, size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long patientId) {

		return ResponseEntity.ok(orderService.findAll(filter, pageable, patientId));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateOrderRequest request,
			@PathVariable Long patientId) {

		OrderResponse order = orderService.create(request, patientId);

		Map<String, Object> body = new HashMap<>();

		body.put("message", "Orden creada exitosamente");
		body.put("order", order);

		return ResponseEntity.created(URI.create("/api/orders/" + order.getId())).body(body);
	}
	

	@GetMapping("/{id}/report")
	public ResponseEntity<OrderReportResponse> getReport(
	        @PathVariable Long patientId,
	        @PathVariable Long id) {

	    return ResponseEntity.ok(
	            orderService.getReport(id, patientId)
	    );
	}

}
