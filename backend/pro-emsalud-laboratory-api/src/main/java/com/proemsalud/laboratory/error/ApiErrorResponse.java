package com.proemsalud.laboratory.error;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
	
	private String timestamp;
	
    private int status;
    
    private String error;
    
    private String message;
    
    private Map<String, String> errors;   // solo se llena en errores de validación
    
    private String detail;                 // solo se llena en errores de BD/constraint

}
