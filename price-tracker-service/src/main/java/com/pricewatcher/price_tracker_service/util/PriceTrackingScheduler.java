package com.pricewatcher.price_tracker_service.util;

import com.pricewatcher.price_tracker_service.service.PriceTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceTrackingScheduler {

    private final PriceTrackingService priceTrackingService;

    @Scheduled(fixedRate = 900000)
    public void trackPrices() {
        priceTrackingService.trackProductPrices();
    }
}
