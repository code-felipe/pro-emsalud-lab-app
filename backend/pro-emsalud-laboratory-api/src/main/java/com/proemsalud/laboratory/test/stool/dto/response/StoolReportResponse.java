package com.proemsalud.laboratory.test.stool.dto.response;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.stool.domain.StoolResult;

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
public class StoolReportResponse {
	
	private Long id;
	
	private StoolResult stoolResult;
	
	private String testName;
	
	private TestMethod testMethod;
	
	private String reference;
}
