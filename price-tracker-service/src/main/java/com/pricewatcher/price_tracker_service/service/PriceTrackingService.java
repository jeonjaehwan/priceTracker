package com.pricewatcher.price_tracker_service.service;

import com.pricewatcher.common_service.enums.Platform;
import com.pricewatcher.common_service.service.EbayService;
import com.pricewatcher.price_tracker_service.repository.PlatformProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceTrackingService {

    private final PlatformProductRepository platformProductRepository;
    private final EbayService ebayService;
    private final KafkaProducer kafkaProducer;

    public void trackProductPrices() {
        platformProductRepository.findAll().stream()
                .filter(product -> product.getPlatformName() == Platform.EBAY)
                .forEach(product -> {
                    try {
                        BigDecimal price = ebayService.getPrice(product.getIdentifierValue());
                        kafkaProducer.sendPriceMessage(product, price);
                    } catch (Exception e) {
                        log.info("Error fetching price for product: {}", product.getId(), e);
                    }
                });
    }

}
