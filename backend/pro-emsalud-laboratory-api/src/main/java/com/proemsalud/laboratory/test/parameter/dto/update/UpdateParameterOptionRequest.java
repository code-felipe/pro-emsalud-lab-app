package com.proemsalud.laboratory.test.parameter.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateParameterOptionRequest {
	
    @NotBlank(message = "El valor es requerido")
    private String value;

    private Integer displayOrder;
    
    @NotNull(message = "El parametro es requerido")
    private Long parameterId;
}
