package com.proemsalud.laboratory.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proemsalud.laboratory.auth.handler.LoginRequest;
import com.proemsalud.laboratory.auth.service.JWTService;
import com.proemsalud.laboratory.auth.service.JWTServiceImpl;
import com.proemsalud.laboratory.user.dto.response.UserResponse;
// Ajusta estos imports a tus paquetes reales
import com.proemsalud.laboratory.user.service.IUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final IUserService userService;
	private final ObjectMapper objectMapper;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
			JWTService jwtService,
			IUserService userService,
			ObjectMapper objectMapper) {

		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userService = userService;
		this.objectMapper = objectMapper;

		this.setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null || password == null) {
			try {
				LoginRequest credentials = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
				username = credentials.getUsername();
				password = credentials.getPassword();
			} catch (IOException e) {
				throw new AuthenticationServiceException("Invalid login request body.", e);
			}
		}

		if (username == null || password == null) {
			throw new AuthenticationServiceException("Invalid or missing credentials in the request.");
		}

		UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(username.trim(), password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);
		String username = authResult.getName();

		// Ajusta el nombre del método a tu servicio (si devuelve Optional, usa .orElseThrow())
		UserResponse user = userService.findByUsername(username);
		user.setPassword(null); // nunca devolver el hash

		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, Object> body = new HashMap<>();
		body.put("token", token);
		body.put("user", user);
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", username));

		response.getWriter().write(objectMapper.writeValueAsString(body));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		// Status y contentType ANTES de escribir el body
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, Object> body = new HashMap<>();
		body.put("mensaje", "Error de autenticación: username o password incorrecto!");
		body.put("error", failed.getMessage());

		response.getWriter().write(objectMapper.writeValueAsString(body));
	}
}