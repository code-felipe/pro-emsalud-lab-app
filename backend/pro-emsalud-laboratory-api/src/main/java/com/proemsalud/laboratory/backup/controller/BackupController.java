package com.proemsalud.laboratory.backup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.response.BackupResponse;
import com.proemsalud.laboratory.backup.enumerate.BackupType;
import com.proemsalud.laboratory.backup.service.IBackupConfigurationService;
import com.proemsalud.laboratory.backup.service.IBackupService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/backup")
public class BackupController {

	@Autowired
	private IBackupService backupService;
	
	@Autowired
	private IBackupConfigurationService configurationService;
	
	
	@GetMapping
	public ResponseEntity<List<BackupResponse>> getAllBackups() {

	    List<BackupResponse> backups = backupService.getAllBackups();

	    return ResponseEntity.ok(backups);
	}


	@PostMapping
	public ResponseEntity<BackupResponse> createBackup() {

		BackupResponse response = backupService.createBackup(BackupType.MANUAL);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/cleanup")
	public ResponseEntity<Void> cleanOldBackups() {

		BackupConfigurationResponse configuration = configurationService.getConfiguration();

		if (configuration == null) {
			return ResponseEntity.notFound().build();
		}

		backupService.cleanOldBackups(configuration.getRetentionDays());

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/latest")
	public ResponseEntity<BackupResponse> getLatestBackup() {

	    BackupResponse backup = backupService.getLatestSuccessfulBackup();

	    if (backup == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(backup);
	}


}
