package com.proemsalud.laboratory.test.simpleschemistry.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.test.simpleschemistry.domain.SimpleChemistry;
import com.proemsalud.laboratory.test.simpleschemistry.dto.create.CreateSimpleChemistryRequest;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryReportResponse;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryResponse;

@Component
public class SimpleChemistryMapper {
	
	public SimpleChemistry toEntity(CreateSimpleChemistryRequest request, Order order, Test test) {
		return SimpleChemistry.builder()
				.result(request.getResult())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	
	public SimpleChemistryResponse toResponse(SimpleChemistry simpleChemistry) {
		return SimpleChemistryResponse.builder()
				.id(simpleChemistry.getId())
				.result(simpleChemistry.getResult())
				.unitPrice(simpleChemistry.getUnitPrice())
				.testName(simpleChemistry.getTest().getName())
				.categoryName(simpleChemistry.getTest().getCategory().getName())
				.testReference(simpleChemistry.getTest().getReference())
				.orderId(simpleChemistry.getOrder().getId())
				.testMethod(simpleChemistry.getTest().getTestMethod())
				.testId(simpleChemistry.getTest().getId())
				.createdAt(simpleChemistry.getCreatedAt())
				.updatedAt(simpleChemistry.getUpdatedAt())
				.build();
	}
	
	public SimpleChemistryReportResponse toReport(SimpleChemistry simpleChemistry) {
		return SimpleChemistryReportResponse.builder()
				.id(simpleChemistry.getId())
				.testName(simpleChemistry.getTest().getName())
				.testMethod(simpleChemistry.getTest().getTestMethod())
				.result(simpleChemistry.getResult())
				.reference(simpleChemistry.getTest().getReference())
				.build();
	}
}
