package com.proemsalud.laboratory.test.parameter.mapper;



import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.domain.ParameterOption;
import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterOptionResponse;

@Component
public class ParameterOptionMapper {
	
	public ParameterOption toEntity( CreateParameterOptionRequest request, Parameter parameter) {

        return ParameterOption.builder()
                .value(request.getValue())
                .displayOrder(request.getDisplayOrder())
                .parameter(parameter)
                .build();
    }

    public ParameterOptionResponse toResponse(ParameterOption option) {
        return ParameterOptionResponse.builder()
                .id(option.getId())
                .value(option.getValue())
                .displayOrder(option.getDisplayOrder())
                .createdAt(option.getCreatedAt())
                .updatedAt(option.getUpdatedAt())
                .build();
    }
}
