package com.proemsalud.laboratory.backup.domain;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "backup_configuration")
public class BackupConfiguration {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled;
    
    @Column(name = "interval_hours")
    private Integer intervalHours;

    @Column(name = "daily_time")
    private LocalTime dailyTime;
    
    @Column(name = "backup_directory")
    private String backupDirectory;
    
    @Column(name = "retention_days")
    private Integer retentionDays;
}
