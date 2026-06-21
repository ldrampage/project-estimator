package com.lxbordo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectEstimatorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProjectEstimatorApplication.class);
        app.setBanner((environment, sourceClass, out) -> {
            out.println("🚀 Lxbordo Custom Spring Boot Api...");
        });
        app.run(args);
	}

}
