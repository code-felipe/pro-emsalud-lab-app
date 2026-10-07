package com.proemsalud.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProEmsaludLaboratoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProEmsaludLaboratoryApiApplication.class, args);
	}

}
