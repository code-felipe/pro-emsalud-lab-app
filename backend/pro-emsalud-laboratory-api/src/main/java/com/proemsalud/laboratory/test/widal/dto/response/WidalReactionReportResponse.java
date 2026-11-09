package com.proemsalud.laboratory.test.widal.dto.response;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.widal.domain.WidalResult;

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
public class WidalReactionReportResponse {
	
	private Long id;
	
	private WidalResult widalResult;
	
	private String testName;
	
	private TestMethod testMethod;
	
	private String reference;
}
