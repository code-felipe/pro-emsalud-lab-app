package com.proemsalud.laboratory.test.parameter.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterResponse;

@Component
public class ParameterMapper {
	
	@Autowired
	private ParameterOptionMapper optionMapper;

    public Parameter toEntity(CreateParameterRequest request) {

        Parameter parameter = Parameter.builder()
                .name(request.getName())
                .examType(request.getExamType())
                .inputType(request.getInputType())
                .displayOrder(request.getDisplayOrder())
                .build();

        if (request.getOptions() != null) {
            request.getOptions().forEach(option ->
                    parameter.getOptions().add(
                            optionMapper.toEntity(option, parameter)
                    )
            );
        }

        return parameter;
    }

    public ParameterResponse toResponse(Parameter parameter) {
        return ParameterResponse.builder()
                .id(parameter.getId())
                .name(parameter.getName())
                .examType(parameter.getExamType())
                .inputType(parameter.getInputType())
                .displayOrder(parameter.getDisplayOrder())
                .createdAt(parameter.getCreatedAt())
                .updatedAt(parameter.getUpdatedAt())
                .options(
                        parameter.getOptions().stream()
                                .map(optionMapper::toResponse)
                                .toList()
                )
                .build();
    }
}
