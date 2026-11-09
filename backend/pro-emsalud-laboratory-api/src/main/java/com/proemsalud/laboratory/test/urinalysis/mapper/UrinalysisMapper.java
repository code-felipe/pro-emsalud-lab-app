package com.proemsalud.laboratory.test.urinalysis.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.test.urinalysis.domain.Urinalysis;
import com.proemsalud.laboratory.test.urinalysis.dto.create.CreateUrinalysisRequest;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisReportResponse;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisResponse;
import com.proemsalud.laboratory.test.widal.domain.WidalReaction;
import com.proemsalud.laboratory.test.widal.dto.create.CreateWidalReactionRequest;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionReportResponse;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionResponse;

@Component
public class UrinalysisMapper {
	
	public Urinalysis toEntity(CreateUrinalysisRequest request, Order order, Test test) {
		return Urinalysis.builder()
				.urinalysisResult(request.getUrinalysisResult())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	public UrinalysisResponse toResponse(Urinalysis uri) {
		return UrinalysisResponse.builder()
				.id(uri.getId())
				.urinalysisResult(uri.getUrinalysisResult())
				.unitPrice(uri.getUnitPrice())
				.testName(uri.getTest().getName())
				.categoryName(uri.getTest().getCategory().getName())
				.testReference(uri.getTest().getReference())
				.orderId(uri.getOrder().getId())
				.testMethod(uri.getTest().getTestMethod())
				.testId(uri.getTest().getId())
				.createdAt(uri.getCreatedAt())
				.updatedAt(uri.getUpdatedAt())
				.build();
	}
	
	public UrinalysisReportResponse toReport(Urinalysis uri) {
		return UrinalysisReportResponse.builder()
				.id(uri.getId())
				.testName(uri.getTest().getName())
				.testMethod(uri.getTest().getTestMethod())
				.urinalysisResult(uri.getUrinalysisResult())
				.reference(uri.getTest().getReference())
				.build();
	}
}
