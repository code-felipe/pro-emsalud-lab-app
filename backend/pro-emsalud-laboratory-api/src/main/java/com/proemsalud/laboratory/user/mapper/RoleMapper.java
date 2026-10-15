package com.proemsalud.laboratory.user.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.user.domain.Role;
import com.proemsalud.laboratory.user.dto.create.CreateRoleRequest;
import com.proemsalud.laboratory.user.dto.response.RoleResponse;


@Component
public class RoleMapper {
	
	
	public Role toEntity(CreateRoleRequest request) {
		return Role.builder().authority(request.getAuthority())
				.build();
				

	}

	public RoleResponse toResponse(Role role) {
		return RoleResponse.builder()
				.id(role.getId())
				.authority(role.getAuthority())
				.build();
	}
}
