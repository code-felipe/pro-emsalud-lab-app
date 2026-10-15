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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

	@Autowired
	private IUserService userService;

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateUserRequest request) {

		UserResponse user = userService.create(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario creado exitosamente");
		body.put("user", user);

		return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(body);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest request) {

		UserResponse user = userService.update(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario actualizado exitosamente");
		body.put("user", user);

		return ResponseEntity.ok(body);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> list() {

		return ResponseEntity.ok(userService.findAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		userService.delete(id);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Usuario borrado exitosamente");

		return ResponseEntity.ok(body);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> upload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam("id") Long id){
		
		UserResponse user = userService.uploadPicture(id, file);
		
		Map<String, Object> body = new HashMap<>();
		body.put("message", "La imagen del usuario subida exitosamente " + file.getName());
		body.put("user", user);
		
		return ResponseEntity.created(URI.create("/api//")).body(body); 
	}
	
	@GetMapping("/uploads/img/{fileName:.+}")
	public ResponseEntity<Resource> viewPicture(
			@PathVariable String fileName){
		
		
		Resource resource = userService.viewPicture(fileName);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + 
				resource.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
}
