package com.proemsalud.laboratory.backup.service;

import java.util.List;

import com.proemsalud.laboratory.backup.domain.Backup;
import com.proemsalud.laboratory.backup.dto.response.BackupResponse;
import com.proemsalud.laboratory.backup.enumerate.BackupType;

public interface IBackupService {
	
    BackupResponse saveBackup(Backup backup);

    BackupResponse getBackup(Long id);
    
    BackupResponse getLatestSuccessfulBackup();
    
    BackupResponse createBackup(BackupType type);
    
    void cleanOldBackups(Integer retentionDays);
    
    List<BackupResponse> getAllBackups();



}
