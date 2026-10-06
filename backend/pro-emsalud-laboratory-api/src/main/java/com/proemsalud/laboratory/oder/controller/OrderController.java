package com.proemsalud.laboratory.oder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;
import com.proemsalud.laboratory.oder.dto.update.UpdateOrderRequest;
import com.proemsalud.laboratory.oder.service.IOrderService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.findById(id));
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateOrderRequest request) {

		OrderResponse order = orderService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Orden actualizada exitosamente");
		body.put("order", order);

		return ResponseEntity.ok(body);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		orderService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Orden borrada exitosamente");

		return ResponseEntity.ok(body);
	}

	@PatchMapping("/{id}/inprogress")
	public ResponseEntity<Map<String, Object>> inProgress(@PathVariable Long id) {
		orderService.inProgress(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Orden en progreso");

		return ResponseEntity.ok(body);
	}

	@PatchMapping("/{id}/completed")
	public ResponseEntity<Map<String, Object>> completed(@PathVariable Long id) {
		orderService.completed(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Orden completada exitosamente");

		return ResponseEntity.ok(body);
	}

}
