package com.pricewatcher.price_tracker_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.pricewatcher.price_tracker_service", "com.pricewatcher.common_service"})
public class PriceTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackerServiceApplication.class, args);
	}

}
