package com.proemsalud.laboratory.test.widal.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.test.widal.domain.WidalReaction;
import com.proemsalud.laboratory.test.widal.dto.create.CreateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionReportResponse;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionResponse;

@Component
public class WidalReactionMapper {
	
	public WidalReaction toEntity(CreateWidalReactionRequest request, Order order, Test test) {
		return WidalReaction.builder()
				.widalResult(request.getWidalResult())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	
	public WidalReactionResponse toResponse(WidalReaction widal) {
		return WidalReactionResponse.builder()
				.id(widal.getId())
				.widalResult(widal.getWidalResult())
				.unitPrice(widal.getUnitPrice())
				.testName(widal.getTest().getName())
				.categoryName(widal.getTest().getCategory().getName())
				.testReference(widal.getTest().getReference())
				.orderId(widal.getOrder().getId())
				.testMethod(widal.getTest().getTestMethod())
				.testId(widal.getTest().getId())
				.createdAt(widal.getCreatedAt())
				.updatedAt(widal.getUpdatedAt())
				.build();
	}
	
	public WidalReactionReportResponse toReport(WidalReaction widal) {
		return WidalReactionReportResponse.builder()
				.id(widal.getId())
				.testName(widal.getTest().getName())
				.testMethod(widal.getTest().getTestMethod())
				.widalResult(widal.getWidalResult())
				.reference(widal.getTest().getReference())
				.build();
	}
}
