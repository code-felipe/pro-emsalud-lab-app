package com.proemsalud.laboratory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proemsalud.laboratory.auth.filter.JWTAuthenticationFilter;
import com.proemsalud.laboratory.auth.filter.JWTAuthorizationFilter;
import com.proemsalud.laboratory.auth.service.JWTService;
import com.proemsalud.laboratory.user.service.IUserService;
import com.proemsalud.laboratory.user.service.JpaUserDetailsService;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Autowired
	public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
			IUserService userService,
			ObjectMapper objectMapper) throws Exception {

		AuthenticationManager authManager = authenticationManager();

		// Filtro que procesa POST /api/login y genera el JWT
		JWTAuthenticationFilter authenticationFilter =
				new JWTAuthenticationFilter(authManager, jwtService, userService, objectMapper);

		// Filtro que valida el JWT de las peticiones posteriores
		JWTAuthorizationFilter authorizationFilter = new JWTAuthorizationFilter(authManager, jwtService);

		authenticationFilter.setFilterProcessesUrl("/api/login");

		http.cors(cors -> {
		}).csrf(csrf -> csrf.disable())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authz -> authz
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/users/uploads/img/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/images/**").permitAll()
						.requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll())
				.addFilter(authenticationFilter)
				.addFilterBefore(authorizationFilter,
						org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:4200"));

		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));

		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.setStatus(403);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Map<String, Object> body = new HashMap<>();
			body.put("message", "Access denied: You have not permissions to access this source");
			body.put("error", accessDeniedException.getMessage());

			response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		};
	}
}
