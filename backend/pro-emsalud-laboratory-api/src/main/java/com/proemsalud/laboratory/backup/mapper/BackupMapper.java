package com.proemsalud.laboratory.backup.mapper;

import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.backup.domain.Backup;
import com.proemsalud.laboratory.backup.dto.response.BackupResponse;

@Component
public class BackupMapper {
	
    public BackupResponse toResponse(Backup backup) {
        return BackupResponse.builder()
                .id(backup.getId())
                .fileName(backup.getFileName())
                .filePath(backup.getFilePath())
                .createdAt(backup.getCreatedAt())
                .size(backup.getSize())
                .status(backup.getStatus())
                .type(backup.getType())
                .errorMessage(backup.getErrorMessage())
                .build();
    }
}
