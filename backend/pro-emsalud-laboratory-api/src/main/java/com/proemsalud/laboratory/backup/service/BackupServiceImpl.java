package com.proemsalud.laboratory.backup.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.backup.config.BackupDatabaseProperties;
import com.proemsalud.laboratory.backup.domain.Backup;
import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.dto.response.BackupResponse;
import com.proemsalud.laboratory.backup.enumerate.BackupStatus;
import com.proemsalud.laboratory.backup.enumerate.BackupType;
import com.proemsalud.laboratory.backup.mapper.BackupMapper;
import com.proemsalud.laboratory.backup.repository.IBackupConfigurationRepository;
import com.proemsalud.laboratory.backup.repository.IBackupRepository;

@Service
public class BackupServiceImpl implements IBackupService {

	@Autowired
	private IBackupRepository repository;

	@Autowired
	private IBackupConfigurationRepository backupConfigurationRepository;

	@Autowired
	private BackupMapper mapper;

	@Autowired
	private BackupDatabaseProperties databaseProperties;

	
	@Override
	public List<BackupResponse> getAllBackups() {

		List<Backup> backups = repository.findAllByOrderByCreatedAtDesc();

		return backups.stream().map(mapper::toResponse).collect(Collectors.toList());
	}
	
	@Override
	public BackupResponse saveBackup(Backup backup) {

		Backup saved = repository.save(backup);

		return mapper.toResponse(saved);
	}

	@Override
	public BackupResponse getBackup(Long id) {

		Backup backup = repository.findById(id).orElse(null);

		if (backup == null) {
			return null;
		}

		return mapper.toResponse(backup);
	}

	@Override
	public BackupResponse getLatestSuccessfulBackup() {

		Backup backup = repository.findFirstByStatusOrderByCreatedAtDesc(BackupStatus.SUCCESS).orElse(null);

		if (backup == null) {
			return null;
		}

		return mapper.toResponse(backup);
	}

	@Override
	public BackupResponse createBackup(BackupType type) {

	    Backup backup = Backup.builder().type(type).build();

	    Path backupPath = null;

	    try {

	        BackupConfiguration configuration = backupConfigurationRepository
	                .findFirstByOrderByIdAsc()
	                .orElseThrow(() ->
	                        new IllegalStateException("No existe una configuración de backup"));

	        createBackupDirectory(configuration);

	        backupPath = buildBackupPath(configuration);

	        List<String> command = buildMysqldumpCommand(backupPath);

	        String errorMessage = executeMysqldump(command, backupPath);

	        if (errorMessage != null) {

	            backup.setStatus(BackupStatus.FAILED);
	            backup.setErrorMessage(errorMessage);
	            backup.setFileName(backupPath.getFileName().toString());
	            backup.setFilePath(backupPath.toString());

	        } else {

	            backup.setStatus(BackupStatus.SUCCESS);
	            backup.setFileName(backupPath.getFileName().toString());
	            backup.setFilePath(backupPath.toString());
	            backup.setSize(getBackupFileSize(backupPath));
	        }

	    } catch (Exception e) {

	        backup.setStatus(BackupStatus.FAILED);
	        backup.setErrorMessage(e.getMessage());

	        if (backupPath != null) {
	            backup.setFileName(backupPath.getFileName().toString());
	            backup.setFilePath(backupPath.toString());
	        }
	    }

	    Backup saved = repository.save(backup);

	    return mapper.toResponse(saved);
	}


	@Override
	public void cleanOldBackups(Integer retentionDays) {

		LocalDateTime limitDate = LocalDateTime.now().minusDays(retentionDays);

		List<Backup> oldBackups = repository.findByCreatedAtBefore(limitDate);

		for (Backup backup : oldBackups) {

			System.out.println("Backup antiguo encontrado: " + backup.getFilePath());

			Path path = Paths.get(backup.getFilePath());

			try {

				Files.deleteIfExists(path);

				repository.delete(backup);

				System.out.println("Backup eliminado: " + backup.getFilePath());

			} catch (Exception e) {

				System.err.println("No se pudo eliminar el backup: " + backup.getFilePath());
			}
		}
	}

	private String generateFileName() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

		return "backup_" + LocalDateTime.now().format(formatter) + ".sql";
	}

	private Path buildBackupPath(BackupConfiguration configuration) {

		String fileName = generateFileName();

		return Paths.get(configuration.getBackupDirectory(), fileName);
	}

	private void createBackupDirectory(BackupConfiguration configuration) throws IOException {

		Path directory = Paths.get(configuration.getBackupDirectory());

		Files.createDirectories(directory);
	}

	private List<String> buildMysqldumpCommand(Path backupPath) {

		return List.of(databaseProperties.getMysqldumpPath(), "-h", databaseProperties.getHost(), "-P",
				databaseProperties.getPort().toString(), "-u", databaseProperties.getUsername(),
				"-p" + databaseProperties.getPassword(), databaseProperties.getDatabase());
	}

	private String executeMysqldump(List<String> command, Path backupPath) throws IOException, InterruptedException {

		ProcessBuilder processBuilder = new ProcessBuilder(command);

		processBuilder.redirectOutput(backupPath.toFile());
		processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

		Process process = processBuilder.start();

		String errorMessage;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

			errorMessage = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		}

		int exitCode = process.waitFor();

		if (exitCode != 0) {
			return errorMessage;
		}

		return null;
	}

	private Long getBackupFileSize(Path backupPath) throws IOException {

		return Files.size(backupPath);
	}



}
