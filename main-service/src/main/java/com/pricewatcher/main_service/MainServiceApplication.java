package com.pricewatcher.main_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.pricewatcher.main_service", "com.pricewatcher.common_service"})
@EntityScan(basePackages = {"com.pricewatcher.main_service.entity", "com.pricewatcher.common_service.entity"})
public class MainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainServiceApplication.class, args);
	}
}
