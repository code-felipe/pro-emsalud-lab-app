package com.proemsalud.laboratory.oder.dto.response;

import java.time.Instant;
import java.util.List;

import com.proemsalud.laboratory.doctor.enumerate.Title;
import com.proemsalud.laboratory.result.dto.response.TestResultReportResponse;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryReportResponse;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryResponse;
import com.proemsalud.laboratory.test.stool.dto.response.StoolReportResponse;
import com.proemsalud.laboratory.test.torch.dto.response.TorchReportResponse;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisReportResponse;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionReportResponse;

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
	
	private List<SimpleChemistryReportResponse> simpleChemistries;
	
	private List<WidalReactionReportResponse> widalReactions;
	
	private List<UrinalysisReportResponse> urinalysis;
	
	private List<StoolReportResponse> stools;
	
	private List<TorchReportResponse> torchs;
	
}
