package com.proemsalud.laboratory.result.dto.response;


import com.proemsalud.laboratory.catalog.enumerate.TestType;

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
public class TestResultReportResponse {
	
	private Long id;
	
	private String testName;
	
	private TestType testType;
	
	private String result;
	
	private String reference;
	
}
