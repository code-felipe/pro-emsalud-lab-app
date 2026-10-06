package com.proemsalud.laboratory.oder.filter;

import java.time.Instant;

import com.proemsalud.laboratory.oder.enumerate.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderFilter {
	
	private String doctorName;
	
	private OrderStatus status;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
