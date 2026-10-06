package com.proemsalud.laboratory.patient.util;

import org.springframework.stereotype.Component;

@Component
public class PatientCodeGenerator {
	
    private static final String PREFIX = "PAC-";

    public String generate(Long patientId) {
        if (patientId == null) {
            throw new IllegalArgumentException("El ID del paciente no puede ser nulo");
        }

        return PREFIX + String.format("%05d", patientId);
    }
}
