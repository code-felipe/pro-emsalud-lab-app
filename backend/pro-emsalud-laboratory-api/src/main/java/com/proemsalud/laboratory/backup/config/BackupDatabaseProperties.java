package com.proemsalud.laboratory.backup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "backup.mysql")
@Getter
@Setter
public class BackupDatabaseProperties {
	
    private String host;

    private Integer port;

    private String database;

    private String username;
    
    private String password;
    
    private String mysqldumpPath;

}
