package com.pricewatcher.price_tracker_service.service;

import com.pricewatcher.common_service.enums.Platform;
import com.pricewatcher.common_service.service.EbayService;
import com.pricewatcher.common_service.dto.AlertMessageReq;
import com.pricewatcher.price_tracker_service.repository.PlatformProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceTrackingService {

    private final PlatformProductRepository platformProductRepository;
    private final EbayService ebayService;
    private final PriceTrackingProducer kafkaProducer;
    private final PriceAlertProducer priceAlertProducer;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void trackProductPrices() {
        platformProductRepository.findAll().stream()
                .filter(product -> product.getPlatformName() == Platform.EBAY)
                .forEach(product -> {
                    try {
                        BigDecimal price = ebayService.getPrice(product.getIdentifierValue());
                        kafkaProducer.sendPriceMessage(product, price);
                        log.info("product {}: {}", product.getId(), price);
                        if (price.compareTo(product.getTargetPrice()) <= 0) {
                            AlertMessageReq alertMessage = AlertMessageReq.from(product.getId(), product.getProduct().getName(), product.getTargetPrice(), price);
                            priceAlertProducer.sendAlertMessage(alertMessage);
                            log.info("Sent alert for product {}: Target Price {}, Current Price {}", product.getId(), product.getTargetPrice(), price);
                        }
                    } catch (Exception e) {
                        log.info("Error fetching price for product: {}", product.getId(), e);
                    }
                });
    }

}
