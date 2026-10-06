package com.proemsalud.laboratory.oder.dto.response;

import java.time.Instant;
import java.util.List;

import com.proemsalud.laboratory.doctor.enumerate.Title;
import com.proemsalud.laboratory.result.dto.response.TestResultReportResponse;

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
public class OrderReportResponse {
	
	private Long id;
	
	private Long patientId;
	
	private String patientFullName;
	
	private String patientPhoneNumber;
	
	private String patientAge;
	
	private String doctorFullName;
	
	private Title doctorTitle;
	
	private Instant createdAt;
	
	private List<TestResultReportResponse> results;
	
}
