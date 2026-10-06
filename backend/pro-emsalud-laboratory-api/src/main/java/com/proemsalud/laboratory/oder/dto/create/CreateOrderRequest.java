package com.proemsalud.laboratory.oder.dto.create;




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
public class CreateOrderRequest {
	
	@NotNull(message = "El doctor es obligatorio")
	private Long doctorId;
}
