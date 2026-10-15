package com.proemsalud.laboratory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.proemsalud.laboratory.user.service.JpaUserDetailsService;

@Configuration
public class AppConfig {
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 logger.info("Hash de 123456: " + encoder.encode("123456"));
        return new BCryptPasswordEncoder();
    }
}
