package com.proemsalud.laboratory.backup.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.proemsalud.laboratory.backup.enumerate.BackupStatus;
import com.proemsalud.laboratory.backup.enumerate.BackupType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "backup")
public class Backup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fileName;

	private String filePath;

	private LocalDateTime createdAt;

	private Long size;

	@Enumerated(EnumType.STRING)
	private BackupStatus status;

	@Enumerated(EnumType.STRING)
	private BackupType type;

	private String errorMessage;
	
	
	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	}

}
