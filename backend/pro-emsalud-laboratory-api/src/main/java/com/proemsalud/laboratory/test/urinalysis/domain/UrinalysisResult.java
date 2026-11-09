package com.proemsalud.laboratory.test.urinalysis.domain;


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
public class UrinalysisResult {
	
	@NotBlank(message = "El color es requerido")
	private String color;
	@NotBlank(message = "La apariencia es requerida")
	private String appearance;
	@NotBlank(message = "La densidad es requerida")
	@Column(name = "specific_gravity")
	private String specificGravity;

	// EXAMEN QUIMICO
	@Column(name = "pH")
	@JsonProperty("pH")
	@NotBlank(message = "El pH es requerido")
	private String pH;
	@NotBlank(message = "Leucocito en quimico es requerido")
	private String leukocytes;
	@NotBlank(message = "Nitrito es requerido")
	private String nitrites;
	@NotBlank(message = "Urobiligeno es requerido")
	private String urobilinogen;
	@NotBlank(message = "La proteina es requerida")
	private String protein;
	@NotBlank(message = "La glucosa es requerida")
	private String glucose;
	@NotBlank(message = "Cetona son requerida")
	private String ketones;
	@NotBlank(message = "La bilirrubina es requerida")
	private String bilirubin;
	@NotBlank(message = "Eritrocito son requeridos")
	private String erythrocytes;

	// EXAMEN MICROSCOPICO
	@NotBlank(message = "Leucocito en microscopio es requerido")
	@Column(name = "microscopic_leukocytes")
	private String microscopicLeukocytes;
	@NotBlank(message = "Eritrocito en microscopio es requerido")
	@Column(name = "microscopic_erythrocytes")
	private String microscopicErythrocytes;
	@NotBlank(message = "Las celulas epiteliales en microscopio es requerido")
	@Column(name = "epithelial_cells") 
	private String epithelialCells;
	@NotBlank(message = "La bacteria es requerida")
	private String bacteria;
	@NotBlank(message = "Moco es requerido")
	private String mucus;
	@NotBlank(message = "Cristales es requerido")
	private String crystals;
	@NotBlank(message = "La levadura es requerida")
	private String yeast;
	private String others;

	private String observation;
}
