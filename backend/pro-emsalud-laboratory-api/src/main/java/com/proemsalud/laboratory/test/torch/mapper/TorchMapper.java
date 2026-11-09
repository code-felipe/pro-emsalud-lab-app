package com.proemsalud.laboratory.test.torch.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.stool.dto.create.CreateStoolRequest;
import com.proemsalud.laboratory.test.stool.dto.response.StoolReportResponse;
import com.proemsalud.laboratory.test.stool.dto.response.StoolResponse;
import com.proemsalud.laboratory.test.torch.domain.Torch;
import com.proemsalud.laboratory.test.torch.dto.create.CreateTorchRequest;
import com.proemsalud.laboratory.test.torch.dto.response.TorchReportResponse;
import com.proemsalud.laboratory.test.torch.dto.response.TorchResponse;

@Component
public class TorchMapper {
	
	public Torch toEntity(CreateTorchRequest request, Order order, Test test) {
		return Torch.builder()
				.torchIgG(request.getTorchIgG())
				.torchIgM(request.getTorchIgM())
				.unitPrice(test.getPrice())
				.order(order)
				.test(test)
				.build();
	}
	
	public TorchResponse toResponse(Torch torch) {
		return TorchResponse.builder()
				.id(torch.getId())
				.torchIgG(torch.getTorchIgG())
				.torchIgM(torch.getTorchIgM())
				.unitPrice(torch.getUnitPrice())
				.testName(torch.getTest().getName())
				.categoryName(torch.getTest().getCategory().getName())
				.testReference(torch.getTest().getReference())
				.orderId(torch.getOrder().getId())
				.testMethod(torch.getTest().getTestMethod())
				.testId(torch.getTest().getId())
				.createdAt(torch.getCreatedAt())
				.updatedAt(torch.getUpdatedAt())
				.build();
	}
	
	public TorchReportResponse toReport(Torch torch) {
		return TorchReportResponse.builder()
				.id(torch.getId())
				.testName(torch.getTest().getName())
				.testMethod(torch.getTest().getTestMethod())
				.torchIgG(torch.getTorchIgG())
				.torchIgM(torch.getTorchIgM())
				.reference(torch.getTest().getReference())
				.build();
	}
}
