package com.pricewatcher.price_tracker_service.service;

import com.pricewatcher.common_service.dto.AlertMessageReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceAlertProducer {

    private final KafkaTemplate<String, AlertMessageReq> kafkaTemplate;

    public void sendAlertMessage(AlertMessageReq alertMessageReq) {
        kafkaTemplate.send("price-alert-topic", alertMessageReq);
    }


}
