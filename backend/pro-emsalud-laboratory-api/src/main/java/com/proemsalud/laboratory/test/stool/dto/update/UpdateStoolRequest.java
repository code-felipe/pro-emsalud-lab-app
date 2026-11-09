package com.proemsalud.laboratory.test.stool.dto.update;

import com.proemsalud.laboratory.test.stool.domain.StoolResult;

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
public class UpdateStoolRequest {
	
	@NotNull(message = "El test es requerido")
	private Long testId;
	
	@NotNull(message = "Los resultados de Heces son requeridos")
    @Valid
	private StoolResult stoolResult;
}
