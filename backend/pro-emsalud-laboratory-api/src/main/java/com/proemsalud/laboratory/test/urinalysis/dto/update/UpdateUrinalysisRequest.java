package com.proemsalud.laboratory.test.urinalysis.dto.update;

import com.proemsalud.laboratory.test.urinalysis.domain.UrinalysisResult;

import jakarta.validation.Valid;
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
public class UpdateUrinalysisRequest {
	
	@NotNull(message = "El test es requerido")
	private Long testId;
	
	@NotNull(message = "Los resultados de Urinalysis son requeridos")
    @Valid
	private UrinalysisResult urinalysisResult;
}
