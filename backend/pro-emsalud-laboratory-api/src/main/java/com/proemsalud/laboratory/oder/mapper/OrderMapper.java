package com.proemsalud.laboratory.oder.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.dto.create.CreateOrderRequest;
import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;
import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.result.dto.response.TestResultReportResponse;
import com.proemsalud.laboratory.result.mapper.TestResultMapper;

@Component
public class OrderMapper {
		
	@Autowired
	private TestResultMapper testResultMapper;
	
	public Order toEntity(CreateOrderRequest request, Doctor doctor, Patient patient) {
		return Order.builder()
			.doctor(doctor)
			.patient(patient)
			.build();
	}

	public OrderResponse toResponse(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.status(order.getStatus())
			.patientId(order.getPatient().getId())
			.doctorId(order.getDoctor().getId())
			.doctorName(order.getDoctor().getFirstName())
			.doctorFullName(order.getDoctor().getFullName())
			.totalOrder(order.calcTotalPriceTestResults())
			.doctorTitle(order.getDoctor().getTitle())
			.createdAt(order.getCreatedAt())
			.updatedAt(order.getUpdatedAt())
			.build();
	}
	
	public OrderReportResponse toReport(Order order) {

	    List<TestResultReportResponse> results = order.getResults()
	            .stream()
	            .map(testResult -> testResultMapper.toReport(testResult))
	            .toList();
	    
	    System.out.println(
	            "TELÉFONO PACIENTE: " +
	            order.getPatient().getPrimaryPhoneNumber()
	        );

	    return OrderReportResponse.builder()
	            .id(order.getId())
	            .patientId(order.getPatient().getId())
	            .patientFullName(order.getPatient().getFullName())
	            .patientAge(order.getPatient().calculateAgeDisplay())
	            .patientPhoneNumber(order.getPatient().getPrimaryPhoneNumber())
	            .doctorFullName(order.getDoctor().getFullName())
	            .doctorTitle(order.getDoctor().getTitle())
	            .createdAt(order.getCreatedAt())
	            .results(results)
	            .build();
	}


	
//	private Double totalOrder(Order order) {
//		
//		Double total = order.getResults().stream()
//				.map(price -> price.get)
//	}
	
}
