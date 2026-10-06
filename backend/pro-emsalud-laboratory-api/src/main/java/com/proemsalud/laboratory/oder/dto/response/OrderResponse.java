package com.proemsalud.laboratory.oder.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.proemsalud.laboratory.doctor.enumerate.Title;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	
	private Long id;
	
	private OrderStatus status;
	
	private Long patientId;	
	
	private Long doctorId;
	
	private String doctorName;
	
	private Instant createdAt;

	private Instant updatedAt;
	
	private String doctorFullName;
	
	private Title doctorTitle;
	
	private BigDecimal totalOrder;
}
