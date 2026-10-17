package com.proemsalud.laboratory.auth.controller;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.proemsalud.laboratory.auth.handler.LoginRequest;
import com.proemsalud.laboratory.auth.service.JWTService;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) throws IOException {

        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

        String token = jwtService.create(authentication);

        return ResponseEntity.ok(
            Map.of("token", token)
        );
    }
}
