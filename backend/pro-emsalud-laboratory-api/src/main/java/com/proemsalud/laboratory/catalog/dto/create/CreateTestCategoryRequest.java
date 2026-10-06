package com.proemsalud.laboratory.catalog.dto.create;



import jakarta.validation.constraints.NotBlank;
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
public class CreateTestCategoryRequest {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String name;
	
}
