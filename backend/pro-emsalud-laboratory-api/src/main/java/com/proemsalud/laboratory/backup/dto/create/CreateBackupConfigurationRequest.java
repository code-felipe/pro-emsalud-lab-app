package com.proemsalud.laboratory.backup.dto.create;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateBackupConfigurationRequest {
	
    private boolean enabled;

    @NotNull(message = "El intervalo de backup es obligatorio")
    private Integer intervalHours;

    @NotNull(message = "La hora diaria del backup es obligatoria")
    private LocalTime dailyTime;

    @NotBlank(message = "El directorio de backup es obligatorio")
    private String backupDirectory;

    @NotNull(message = "Los días de retención son obligatorios")
    private Integer retentionDays;
	
}
