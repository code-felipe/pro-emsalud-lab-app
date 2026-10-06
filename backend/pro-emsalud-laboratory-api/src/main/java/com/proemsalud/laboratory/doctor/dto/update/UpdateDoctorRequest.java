package com.proemsalud.laboratory.doctor.dto.update;

import com.proemsalud.laboratory.doctor.enumerate.Title;

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
public class UpdateDoctorRequest {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String firstName;
	
	@NotBlank(message = "El apellido es obligatorio")
	private String lastName;
	
	@NotNull(message = "El titulo del doctor(a) es requerido")
	private Title title;
}
