package com.proemsalud.laboratory.test.widal.dto.create;

import com.proemsalud.laboratory.test.widal.domain.WidalResult;

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
public class CreateWidalReactionRequest {
	
	private Double unitPrice;
	
	@NotNull(message = "El test es requerido")
	private Long testId;
	
    @NotNull(message = "Los resultados de Widal son requeridos")
    @Valid
	private WidalResult widalResult;
}
