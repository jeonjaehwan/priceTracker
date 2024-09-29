package com.pricewatcher.price_alert_service.service;

import com.pricewatcher.price_alert_service.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final SmsUtil smsUtil;

    public void sendPriceDropNotification(String phoneNumber, String productName, BigDecimal targetPrice, BigDecimal currentPrice) {
        try {
//            smsUtil.sendOne(phoneNumber, productName, targetPrice, currentPrice);
            log.info("Price drop notification sent to {} for product {} with current price {}", phoneNumber, productName, currentPrice);
        } catch (Exception e) {
            log.error("notification to {}: {}", phoneNumber, e.getMessage());
        }
    }
}