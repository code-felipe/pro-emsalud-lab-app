package com.proemsalud.laboratory.user.dto.response;

import java.time.Instant;

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
public class UserResponse {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
	private String picture;
	
    private Instant createdAt;
    
    private Instant updatedAt;
    
    private RoleResponse role;
}
