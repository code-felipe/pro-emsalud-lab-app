package com.proemsalud.laboratory.result.dto.update;

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
public class UpdateTestResultRequest {
	
	
    @NotBlank(message = "El resultado del test es requerido")
	private String result;
	
	private Double unitPrice;
	
	@NotNull(message = "El test es requerido")
	private Long testId;
	
}
