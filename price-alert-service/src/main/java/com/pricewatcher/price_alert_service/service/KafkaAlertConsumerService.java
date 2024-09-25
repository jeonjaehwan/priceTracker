package com.pricewatcher.price_alert_service.service;

import com.pricewatcher.common_service.dto.AlertMessageReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KafkaAlertConsumerService {

    private final NotificationService notificationService;
    private final UserService userService;

    @KafkaListener(topics = "price-alert-topic", groupId = "price-alert-group")
    public void consumerPriceMessage(AlertMessageReq alertMessageReq) {
        String userPhoneNumber = userService.getUserPhoneNumber(alertMessageReq.getUserId());
        notificationService.sendPriceDropNotification(
                userPhoneNumber,
                alertMessageReq.getProductName(),
                alertMessageReq.getTargetPrice(),
                alertMessageReq.getCurrentPrice()
        );

    }
}
