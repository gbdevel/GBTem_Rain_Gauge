package com.gbsoft.rainfallcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gbsoft.rainfallcollector.domain.AuditorAwareImpl;

@EnableScheduling
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@SpringBootApplication
public class RainfallCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainfallCollectorApplication.class, args);
	}

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

}
