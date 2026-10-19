package com.proemsalud.laboratory.user.dto.update;

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
public class UpdateUserRequest {
	
	@NotBlank(message = "El nombre de usuario es requerido")
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
	private String picture;
	
	@NotNull(message = "El role debe tener al menos un role")
	private Long roleId;
}
