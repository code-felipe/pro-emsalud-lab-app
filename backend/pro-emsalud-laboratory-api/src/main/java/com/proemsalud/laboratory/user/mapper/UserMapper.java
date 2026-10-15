package com.proemsalud.laboratory.user.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.user.domain.User;
import com.proemsalud.laboratory.user.dto.create.CreateUserRequest;
import com.proemsalud.laboratory.user.dto.response.RoleResponse;
import com.proemsalud.laboratory.user.dto.response.UserResponse;

@Component
public class UserMapper {

	public User toEntity(CreateUserRequest request) {
		return User.builder().username(request.getUsername()).password(request.getPassword())
				.enabled(request.getEnabled()).build();

	}

	public UserResponse toResponse(User user) {
		return UserResponse.builder().
				id(user.getId())
				.username(user.getUsername())
				.enabled(user.getEnabled())
				.picture(user.getPicture())
				.createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt())
				.roles(user.getRoles().stream()
						.map(role -> RoleResponse.builder()
								.id(role.getId())
								.authority(role.getAuthority())
								.build())
						.toList())
				.build();
	}

	private String normalizeBlank(String value) {
		return (value == null || value.isBlank()) ? null : value;
	}
}
