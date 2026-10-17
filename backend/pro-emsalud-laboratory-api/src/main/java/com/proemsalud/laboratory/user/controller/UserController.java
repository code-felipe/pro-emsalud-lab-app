package com.proemsalud.laboratory.user.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.proemsalud.laboratory.user.dto.create.CreateUserRequest;
import com.proemsalud.laboratory.user.dto.response.UserResponse;
import com.proemsalud.laboratory.user.dto.update.UpdateUserRequest;
import com.proemsalud.laboratory.user.service.IUserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final String ADMIN_OR_SELF = "hasRole('ADMIN') or @userSecurity.isSelf(#id, authentication)";

	@Autowired
	private IUserService userService;

	// ───────── SOLO ADMIN ─────────

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateUserRequest request) {

		UserResponse user = userService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario creado exitosamente");
		body.put("user", user);

		return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(body);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserResponse>> list() {

		return ResponseEntity.ok(userService.findAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		userService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario borrado exitosamente");

		return ResponseEntity.ok(body);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/toggle-status")
	public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {

		userService.toggleStatus(id);

		return ResponseEntity.noContent().build();
	}

	// ───────── ADMIN O EL MISMO USUARIO ─────────

	@PreAuthorize(ADMIN_OR_SELF)
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.findById(id));
	}

	@PreAuthorize(ADMIN_OR_SELF)
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest request) {

		UserResponse user = userService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario actualizado exitosamente");
		body.put("user", user);

		return ResponseEntity.ok(body);
	}

	@PreAuthorize(ADMIN_OR_SELF)
	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("id") Long id) {

		UserResponse user = userService.uploadPicture(id, file);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "La imagen del usuario subida exitosamente " + file.getOriginalFilename());
		body.put("user", user);

		return ResponseEntity.created(URI.create("/api/users/" + id)).body(body);
	}

	// ───────── CUALQUIER USUARIO LOGUEADO ─────────

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping("/me")
	public ResponseEntity<UserResponse> me(Authentication authentication) {

		UserResponse user = userService.findByUsername(authentication.getName());
		user.setPassword(null);

		return ResponseEntity.ok(user);
	}

	// ───────── PÚBLICO (lo pide el <img>, que no manda token) ─────────

	@PreAuthorize("permitAll()")
	@GetMapping("/uploads/img/{fileName:.+}")
	public ResponseEntity<Resource> viewPicture(@PathVariable String fileName) {

		Resource resource = userService.viewPicture(fileName);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<>(resource, header, HttpStatus.OK);
	}
}