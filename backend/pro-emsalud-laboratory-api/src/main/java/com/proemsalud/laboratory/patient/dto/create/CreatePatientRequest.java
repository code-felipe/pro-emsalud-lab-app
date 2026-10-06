package com.proemsalud.laboratory.patient.dto.create;

import java.time.LocalDate;

import com.proemsalud.laboratory.patient.enumerate.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CreatePatientRequest {
	
    @NotNull(message = "El género es requerido")
    private Gender gender;

    @NotBlank(message = "El primer nombre es requerido")
    private String firstName;

    private String middleName;

    @NotBlank(message = "El apellido paterno es requerido")
    private String fatherLastName;

    private String motherLastName;

    @NotNull(message = "La fecha de nacimiento es requerida")
    private LocalDate dateOfBirth;

    @Pattern(
    	    regexp = "^[2345][0-9]{7}$",
    	    message = "El teléfono debe tener 8 dígitos"
    	)
    private String primaryPhoneNumber;
    
    @Pattern(
    	    regexp = "^[2345][0-9]{7}$",
    	    message = "El teléfono debe tener 8 dígitos"
    	)
    private String optionalPhoneNumber;
    
    @NotBlank(message = "La ciudad es requerida")
    private String city;
}
