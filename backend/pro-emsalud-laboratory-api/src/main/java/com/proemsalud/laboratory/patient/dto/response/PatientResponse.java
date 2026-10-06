package com.proemsalud.laboratory.patient.dto.response;

import java.time.Instant;
import java.time.LocalDate;

import com.proemsalud.laboratory.patient.enumerate.Gender;

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
public class PatientResponse {
	
	private Long id;
	
	private String code;
	
    private Gender gender;
    
    private String firstName;

    private String middleName;

    private String fatherLastName;

    private String motherLastName;

    private LocalDate dateOfBirth;
    
    private String age;

    private String primaryPhoneNumber;

    private String optionalPhoneNumber;
    
    private String city;
    
    private Instant createdAt;
    
    private Instant updatedAt;
    
    
}
