package com.proemsalud.laboratory.test.stool.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.stool.dto.create.CreateStoolRequest;
import com.proemsalud.laboratory.test.stool.dto.response.StoolReportResponse;
import com.proemsalud.laboratory.test.stool.dto.response.StoolResponse;

@Component
public class StoolMapper {
	
	public Stool toEntity(CreateStoolRequest request, Order order, Test test) {
		return Stool.builder()
				.stoolResult(request.getStoolResult())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	public StoolResponse toResponse(Stool stool) {
		return StoolResponse.builder()
				.id(stool.getId())
				.stoolResult(stool.getStoolResult())
				.unitPrice(stool.getUnitPrice())
				.testName(stool.getTest().getName())
				.categoryName(stool.getTest().getCategory().getName())
				.testReference(stool.getTest().getReference())
				.orderId(stool.getOrder().getId())
				.testMethod(stool.getTest().getTestMethod())
				.testId(stool.getTest().getId())
				.createdAt(stool.getCreatedAt())
				.updatedAt(stool.getUpdatedAt())
				.build();
	}
	
	public StoolReportResponse toReport(Stool stool) {
		return StoolReportResponse.builder()
				.id(stool.getId())
				.testName(stool.getTest().getName())
				.testMethod(stool.getTest().getTestMethod())
				.stoolResult(stool.getStoolResult())
				.reference(stool.getTest().getReference())
				.build();
	}
}
