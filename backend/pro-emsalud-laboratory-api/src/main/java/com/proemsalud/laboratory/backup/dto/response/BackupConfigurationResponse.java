package com.proemsalud.laboratory.backup.dto.response;

import java.time.LocalTime;


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
public class BackupConfigurationResponse {
	
	private Long id;
	
    private boolean enabled;

    private Integer intervalHours;

    private LocalTime dailyTime;

    private String backupDirectory;

    private Integer retentionDays;
}
