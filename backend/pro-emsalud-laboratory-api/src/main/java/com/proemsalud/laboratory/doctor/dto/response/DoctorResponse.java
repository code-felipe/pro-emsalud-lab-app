package com.proemsalud.laboratory.doctor.dto.response;

import java.time.Instant;

import com.proemsalud.laboratory.doctor.enumerate.Title;
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
public class DoctorResponse {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private Title title;
	
	private Instant createdAt;

	private Instant updatedAt;
	
	private String fullName;
	
}
