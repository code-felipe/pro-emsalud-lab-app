package com.proemsalud.laboratory;

import java.time.Instant;
import java.time.LocalTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.backup.domain.BackupConfiguration;
import com.proemsalud.laboratory.backup.repository.IBackupConfigurationRepository;
import com.proemsalud.laboratory.user.domain.Role;
import com.proemsalud.laboratory.user.domain.User;
import com.proemsalud.laboratory.user.repository.IRoleRepository;
import com.proemsalud.laboratory.user.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
	
	
	private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IBackupConfigurationRepository backupRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        Role admin = findOrCreateRole("ROLE_ADMIN");
        findOrCreateRole("ROLE_USER");

        if (userRepository.findByUsername("carlos").isEmpty()) {
            User u = new User();
            u.setUsername("carlos");
            u.setPassword(passwordEncoder.encode("kaeell23"));
            u.setRole(admin);
            u.setCreatedAt(Instant.now());
            u.setUpdatedAt(Instant.now());
            userRepository.save(u);
        }

        if (backupRepository.count() == 0) {
            BackupConfiguration c = new BackupConfiguration();
            c.setEnabled(true);
            c.setIntervalHours(6);
            c.setDailyTime(LocalTime.of(2, 0));
            c.setBackupDirectory("C:\\LabBackups");
            c.setRetentionDays(14);
            backupRepository.save(c);
        }
    }

    private Role findOrCreateRole(String authority) {
        return roleRepository.findByAuthority(authority).orElseGet(() -> {
            Role r = new Role();
            r.setAuthority(authority);
            return roleRepository.save(r);
        });
    }
}
