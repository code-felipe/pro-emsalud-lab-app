package com.proemsalud.laboratory.test.torch.dto.response;

import com.proemsalud.laboratory.catalog.enumerate.TestMethod;
import com.proemsalud.laboratory.test.stool.domain.StoolResult;
import com.proemsalud.laboratory.test.torch.domain.TorchIgG;
import com.proemsalud.laboratory.test.torch.domain.TorchIgM;

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
public class TorchReportResponse {
	
	private Long id;
	
	private TorchIgG torchIgG;
	
	private TorchIgM torchIgM;
	
	private String testName;
	
	private TestMethod testMethod;
	
	private String reference;
}
