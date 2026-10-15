package com.proemsalud.laboratory.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.user.dto.response.RoleResponse;
import com.proemsalud.laboratory.user.service.IRoleService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<RoleResponse>> list() {

		return ResponseEntity.ok(roleService.findAll());
	}
}
