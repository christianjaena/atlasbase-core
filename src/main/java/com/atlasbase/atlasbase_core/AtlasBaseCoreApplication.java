package com.atlasbase.atlasbase_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.atlasbase.atlasbase_core.infrastructure.persistence.jpa")
public class AtlasBaseCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtlasBaseCoreApplication.class, args);
	}

}
