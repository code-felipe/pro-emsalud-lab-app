package com.proemsalud.laboratory.backup.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.backup.domain.Backup;
import com.proemsalud.laboratory.backup.enumerate.BackupStatus;

public interface IBackupRepository extends JpaRepository<Backup, Long>, JpaSpecificationExecutor<Backup> {

	Optional<Backup> findFirstByStatusOrderByCreatedAtDesc(BackupStatus status); //find the last success backup
	
	List<Backup> findByCreatedAtBefore(LocalDateTime date);
	
	List<Backup> findAllByOrderByCreatedAtDesc();



}
