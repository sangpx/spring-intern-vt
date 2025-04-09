package com.demo.project_intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectInternApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectInternApplication.class, args);
	}

}
