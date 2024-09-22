package com.pricewatcher.price_tracker_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.pricewatcher.price_tracker_service", "com.pricewatcher.common_service"})
@EntityScan(basePackages = {"com.pricewatcher.main_service.entity", "com.pricewatcher.common_service.entity"})
@EnableJpaAuditing
@EnableScheduling
public class PriceTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackerServiceApplication.class, args);
	}

}
