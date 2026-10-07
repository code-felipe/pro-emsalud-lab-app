package com.proemsalud.laboratory.backup.dto.response;

import java.time.LocalDateTime;

import com.proemsalud.laboratory.backup.enumerate.BackupStatus;
import com.proemsalud.laboratory.backup.enumerate.BackupType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackupResponse {

	private Long id;

	private String fileName;

	private String filePath;

	private LocalDateTime createdAt;

	private Long size;

	private BackupStatus status;

	private BackupType type;

	private String errorMessage;
}
