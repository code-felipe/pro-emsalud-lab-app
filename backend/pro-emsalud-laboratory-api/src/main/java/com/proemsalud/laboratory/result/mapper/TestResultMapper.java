package com.proemsalud.laboratory.result.mapper;

import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.result.domain.TestResult;
import com.proemsalud.laboratory.result.dto.create.CreateTestResultRequest;
import com.proemsalud.laboratory.result.dto.response.TestResultReportResponse;
import com.proemsalud.laboratory.result.dto.response.TestResultResponse;

@Component
public class TestResultMapper {

	public TestResult toEntity(CreateTestResultRequest request, Order order, Test test) {
		return TestResult.builder()
				.result(request.getResult())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	
	public TestResultResponse toResponse(TestResult testResult) {
		return TestResultResponse.builder()
				.id(testResult.getId())
				.result(testResult.getResult())
				.unitPrice(testResult.getUnitPrice())
				.testName(testResult.getTest().getName())
				.categoryName(testResult.getTest().getCategory().getName())
				.testReference(testResult.getTest().getReference())
				.orderId(testResult.getOrder().getId())
				.testMethods(new TreeSet<>(testResult.getTest().getTestMethods()))
				.testId(testResult.getTest().getId())
				.createdAt(testResult.getCreatedAt())
				.updatedAt(testResult.getUpdatedAt())
				.build();
	}

	public TestResultReportResponse toReport(TestResult testResult) {
		return TestResultReportResponse.builder()
				.id(testResult.getId())
				.testName(testResult.getTest().getName())
				.testMethods(new TreeSet<>(testResult.getTest().getTestMethods()))
				.result(testResult.getResult())
				.reference(testResult.getTest().getReference())
				.build();
	}
}
