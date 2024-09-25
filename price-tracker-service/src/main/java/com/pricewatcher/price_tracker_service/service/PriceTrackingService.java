package com.pricewatcher.price_tracker_service.service;

import com.pricewatcher.common_service.entity.User;
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
    private final PriceTrackingProducer priceTrackingProducer;
    private final PriceAlertProducer priceAlertProducer;

    @Transactional
    public void trackProductPrices() {
        platformProductRepository.findAll().stream()
                .filter(product -> product.getPlatformName() == Platform.EBAY)
                .forEach(product -> {
                    try {
                        BigDecimal price = ebayService.getPrice(product.getIdentifierValue());
                        priceTrackingProducer.sendPriceMessage(product, price);
                        log.info("product {}: Current Price {}, Target Price {}", product.getProductName(), price, product.getTargetPrice());
                        if (price.compareTo(product.getTargetPrice()) <= 0) {
                            User user = product.getUser();
                            AlertMessageReq alertMessage = AlertMessageReq.from(product.getId(), product.getProductName(), product.getTargetPrice(), price, user.getId());
                            priceAlertProducer.sendAlertMessage(alertMessage);
                            log.info("Sent alert for product {}: Target Price {}, Current Price {}", product.getId(), product.getTargetPrice(), price);
                        }
                    } catch (Exception e) {
                        log.info("Error fetching price for product: {}", product.getId(), e);
                    }
                });
    }

}
