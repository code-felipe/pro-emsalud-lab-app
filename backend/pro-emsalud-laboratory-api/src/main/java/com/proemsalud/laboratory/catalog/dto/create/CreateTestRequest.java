package com.proemsalud.laboratory.catalog.dto.create;

import java.math.BigDecimal;

import com.proemsalud.laboratory.catalog.enumerate.TestType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
public class CreateTestRequest {
	
	
	@NotBlank(message = "El nombre es obligatorio")
	private String name;

	@NotNull(message = "El tipo de prueba es obligatorio")
	private TestType testType;

	private String reference; // opcional — puede ser null (ej. Rotavirus)

	@NotNull(message = "El precio es obligatorio")
	@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
	@Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 enteros y 2 decimales")
	private BigDecimal price;

	@NotNull(message = "La categoria es obligatoria")
	private Long testCategoryId;
}
