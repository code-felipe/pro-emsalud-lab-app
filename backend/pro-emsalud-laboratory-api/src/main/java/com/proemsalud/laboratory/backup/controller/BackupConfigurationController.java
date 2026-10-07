package com.proemsalud.laboratory.backup.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.dto.create.CreateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.update.UpdateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.mapper.BackupConfigurationMapper;
import com.proemsalud.laboratory.backup.service.IBackupConfigurationService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/backup/configuration")
public class BackupConfigurationController {

	@Autowired
	private IBackupConfigurationService service;

	@GetMapping
	public ResponseEntity<BackupConfigurationResponse> getConfiguration() {

		BackupConfigurationResponse configuration = service.getConfiguration();

		if (configuration == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(configuration);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateBackupConfigurationRequest request) {

		BackupConfigurationResponse backup = service.createConfiguration(request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Backup creado exitosamente");
		body.put("backup", backup);

		return ResponseEntity.created(URI.create("/api/backup/configuration" + backup.getId())).body(body);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateBackupConfigurationRequest request) {

		BackupConfigurationResponse configuration = service.updateConfiguration(id, request);

		Map<String, Object> body = new HashMap<>();
		body.put("message", "Configuración de backup actualizada exitosamente");
		body.put("configuration", configuration);

		return ResponseEntity.ok(body);
	}

}
