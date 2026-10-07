package com.proemsalud.laboratory.backup.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.dto.create.CreateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.update.UpdateBackupConfigurationRequest;

@Component
public class BackupConfigurationMapper {
	
	public BackupConfiguration toEntity(CreateBackupConfigurationRequest request) {
	    return BackupConfiguration.builder()
	            .enabled(request.isEnabled())
	            .intervalHours(request.getIntervalHours())
	            .dailyTime(request.getDailyTime())
	            .backupDirectory(request.getBackupDirectory())
	            .retentionDays(request.getRetentionDays())
	            .build();
	}

	public BackupConfiguration toEntity(UpdateBackupConfigurationRequest request) {
	    return BackupConfiguration.builder()
	            .id(request.getId())
	            .enabled(request.isEnabled())
	            .intervalHours(request.getIntervalHours())
	            .dailyTime(request.getDailyTime())
	            .backupDirectory(request.getBackupDirectory())
	            .retentionDays(request.getRetentionDays())
	            .build();
	}
	
	public BackupConfigurationResponse toResponse(BackupConfiguration configuration) {

		return BackupConfigurationResponse.builder()
				.id(configuration.getId()).enabled(configuration.isEnabled())
				.intervalHours(configuration.getIntervalHours()).dailyTime(configuration.getDailyTime())
				.backupDirectory(configuration.getBackupDirectory()).retentionDays(configuration.getRetentionDays())
				.build();
	}
	
}
