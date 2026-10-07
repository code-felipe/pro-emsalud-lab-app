package com.proemsalud.laboratory.backup.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;


public interface IBackupConfigurationRepository extends JpaRepository<BackupConfiguration, Long>, JpaSpecificationExecutor<BackupConfiguration>{
	
	Optional<BackupConfiguration> findFirstByOrderByIdAsc();

}
