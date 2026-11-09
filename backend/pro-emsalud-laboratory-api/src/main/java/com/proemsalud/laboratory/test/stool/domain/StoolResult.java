package com.proemsalud.laboratory.test.stool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoolResult {
	

	// EXAMEN MACROSCOPICO
	@NotBlank(message = "El color es requerido")
	private String color;

	@NotBlank(message = "La consistencia es requerida")
	private String consistency;

	@NotBlank(message = "El moco es requerido")
	private String mucus;

	@Column(name = "pH")
	@JsonProperty("pH")
	@NotBlank(message = "El pH es requerido")
	private String pH;

	@NotBlank(message = "La sangre es requerida")
	private String blood;

	@NotBlank(message = "Los restos alimenticios son requeridos")
	@Column(name = "food_remains")
	private String foodRemains;

	// EXAMEN MICROSCOPICO
	@NotBlank(message = "Los leucocitos son requeridos")
	private String leukocytes;

	@NotBlank(message = "Los eritrocitos son requeridos")
	private String erythrocytes;

	@NotBlank(message = "Las levaduras son requeridas")
	private String yeast;

	@NotBlank(message = "Las células vegetales son requeridas")
	@Column(name = "vegetable_cells")
	private String vegetableCells;

	@NotBlank(message = "Las fibras musculares son requeridas")
	@Column(name = "muscle_fibers")
	private String muscleFibers;

	@NotBlank(message = "Las grasas son requeridas")
	private String fats;

	@NotBlank(message = "Los almidones son requeridos")
	private String starches;

	@NotBlank(message = "Los parásitos son requeridos")
	private String parasites;

	@NotBlank(message = "Las bacterias son requeridas")
	private String bacteria;
	
	private String observation;
}
