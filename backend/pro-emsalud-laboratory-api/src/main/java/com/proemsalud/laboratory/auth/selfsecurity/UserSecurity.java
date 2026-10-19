package com.proemsalud.laboratory.auth.selfsecurity;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import com.proemsalud.laboratory.user.repository.IUserRepository;

@Component("userSecurity")
public class UserSecurity {
	
	private final IUserRepository userRepository;

	public UserSecurity(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean isSelf(Long id, Authentication authentication) {
		if (id == null || authentication == null) {
			return false;
		}

		return userRepository.findByUsername(authentication.getName())
				.map(u -> u.getId().equals(id))
				.orElse(false);
	}
}
