package com.proemsalud.laboratory.test.parameter.dto.create;

import java.util.List;

import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.enumerate.InputType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateParameterRequest {
	
    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "El tipo de examen es requerido")
    private ExamType examType;

    @NotNull(message = "El tipo de input es requerido")
    private InputType inputType;

    private Integer displayOrder;
    
    private List<CreateParameterOptionRequest> options;
}
