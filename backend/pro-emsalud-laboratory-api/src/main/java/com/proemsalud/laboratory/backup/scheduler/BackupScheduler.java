package com.proemsalud.laboratory.backup.scheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.response.BackupResponse;
import com.proemsalud.laboratory.backup.enumerate.BackupType;
import com.proemsalud.laboratory.backup.service.IBackupConfigurationService;
import com.proemsalud.laboratory.backup.service.IBackupService;

@Component
public class BackupScheduler {

	@Autowired
	private IBackupConfigurationService configurationService;

	@Autowired
	private IBackupService backupService;

	@Scheduled(fixedRate = 300000)
	public void checkBackup() {

		BackupConfigurationResponse configuration = configurationService.getConfiguration();

		if (configuration == null) {
			return;
		}

		if (!configuration.isEnabled()) {
			return;
		}

		System.out.println("Backups automáticos habilitados");

		// Limpiar backups antiguos
		backupService.cleanOldBackups(configuration.getRetentionDays());

		BackupResponse lastBackup = backupService.getLatestSuccessfulBackup();

		if (lastBackup != null) {
			System.out.println("Último backup: " + lastBackup.getCreatedAt());
		}

		if (shouldCreateBackup(configuration, lastBackup)) {

			System.out.println("Corresponde crear un backup");

			try {

				backupService.createBackup(BackupType.AUTOMATIC);

			} catch (Exception e) {

				System.err.println("Error ejecutando backup automático: " + e.getMessage());
			}

		} else {

			System.out.println("Todavía no corresponde crear un backup");
		}
	}

	private boolean shouldCreateBackup(BackupConfigurationResponse configuration, BackupResponse lastBackup) {

		if (lastBackup == null) {
			return isDailyTimeReached(configuration);
		}

		LocalDateTime nextBackupTime = lastBackup.getCreatedAt().plusHours(configuration.getIntervalHours());

		return !LocalDateTime.now().isBefore(nextBackupTime);
	}

	private boolean isDailyTimeReached(BackupConfigurationResponse configuration) {

		LocalTime now = LocalTime.now();

		return !now.isBefore(configuration.getDailyTime());
	}

}
