package com.proemsalud.laboratory.test.simpleschemistry.dto.response;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;

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
public class SimpleChemistryReportResponse {
	
	private Long id;
	
	private String testName;
	
	private TestMethod testMethod;
	
	private String result;
	
	private String reference;
}
