package com.proemsalud.laboratory.backup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.dto.create.CreateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.update.UpdateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.mapper.BackupConfigurationMapper;
import com.proemsalud.laboratory.backup.repository.IBackupConfigurationRepository;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;

@Service
public class BackupConfigurationServiceImpl implements IBackupConfigurationService {

	@Autowired
	private IBackupConfigurationRepository repository;

	@Autowired
	private BackupConfigurationMapper backupConfigurationMapper;

	@Override
	public BackupConfigurationResponse getConfiguration() {

		BackupConfiguration configuration = repository.findFirstByOrderByIdAsc().orElse(null);

		if (configuration == null) {
			return null;
		}

		return backupConfigurationMapper.toResponse(configuration);
	}

	@Override
	public BackupConfigurationResponse createConfiguration(CreateBackupConfigurationRequest request) {

		BackupConfiguration configuration = backupConfigurationMapper.toEntity(request);

		BackupConfiguration saved = repository.save(configuration);

		return backupConfigurationMapper.toResponse(saved);
	}

	@Override
	public BackupConfigurationResponse updateConfiguration(Long id, UpdateBackupConfigurationRequest request) {

		BackupConfiguration configuration = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("La configuración de backup con id: " + id + " no existe"));

		configuration.setEnabled(request.isEnabled());
		configuration.setIntervalHours(request.getIntervalHours());
		configuration.setDailyTime(request.getDailyTime());
		configuration.setBackupDirectory(request.getBackupDirectory());
		configuration.setRetentionDays(request.getRetentionDays());

		BackupConfiguration updated = repository.save(configuration);

		return backupConfigurationMapper.toResponse(updated);
	}

}
