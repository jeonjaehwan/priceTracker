package com.pricewatcher.price_alert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.pricewatcher.price_alert_service", "com.pricewatcher.common_service"})
@EntityScan(basePackages = {"com.pricewatcher.price-alert-service.entity", "com.pricewatcher.common_service.entity"})
public class PriceAlertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceAlertServiceApplication.class, args);
	}

}
