package com.proemsalud.laboratory.test.urinalysis.dto.response;

import java.util.Set;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.urinalysis.domain.UrinalysisResult;

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
public class UrinalysisReportResponse {
	
	private Long id;
	
	private UrinalysisResult urinalysisResult;
	
	private String testName;
	
	private Set<TestMethod> testMethods;
	
	private String reference;
}
