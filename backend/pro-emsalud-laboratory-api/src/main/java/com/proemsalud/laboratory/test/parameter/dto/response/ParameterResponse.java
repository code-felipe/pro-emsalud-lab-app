package com.proemsalud.laboratory.test.parameter.dto.response;

import java.time.Instant;
import java.util.List;

import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.enumerate.InputType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterResponse {
	
    private Long id;

    private String name;

    private ExamType examType;

    private InputType inputType;

    private Integer displayOrder;

    private Instant createdAt;

    private Instant updatedAt;

    private List<ParameterOptionResponse> options;
}
