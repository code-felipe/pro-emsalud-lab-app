package com.proemsalud.laboratory.backup.service;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.dto.create.CreateBackupConfigurationRequest;
import com.proemsalud.laboratory.backup.dto.response.BackupConfigurationResponse;
import com.proemsalud.laboratory.backup.dto.update.UpdateBackupConfigurationRequest;

public interface IBackupConfigurationService {

	public BackupConfigurationResponse getConfiguration();

	public BackupConfigurationResponse createConfiguration(CreateBackupConfigurationRequest request);

	public BackupConfigurationResponse updateConfiguration(Long id, UpdateBackupConfigurationRequest configuration);
}
