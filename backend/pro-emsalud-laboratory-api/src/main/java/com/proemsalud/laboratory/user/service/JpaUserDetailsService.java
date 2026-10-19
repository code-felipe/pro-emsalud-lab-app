package com.proemsalud.laboratory.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proemsalud.laboratory.user.domain.User;
import com.proemsalud.laboratory.user.repository.IUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Autowired
	private IUserRepository userRepo;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> {
					logger.error("Login Error: user '{}' does not exist in the system", username);
					return new UsernameNotFoundException("Username: " + username + " does not exist in the system!");
				});

		if (user.getRole() == null) {
			logger.error("Login Error: user '{}' does not have an assigned role", username);
			throw new UsernameNotFoundException("User '" + username + "' does not have an assigned role!");
		}

		List<GrantedAuthority> authorities =
				List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()));

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				authorities);
	}
}