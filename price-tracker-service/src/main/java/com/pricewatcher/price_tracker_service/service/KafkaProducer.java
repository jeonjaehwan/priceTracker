package com.pricewatcher.price_tracker_service.service;

import com.pricewatcher.common_service.entity.PlatformProduct;
import com.pricewatcher.price_tracker_service.dto.PriceMessageReq;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PriceMessageReq> kafkaTemplate;

    public void sendPriceMessage(PlatformProduct product, BigDecimal price) {
        PriceMessageReq priceMessage = new PriceMessageReq(product.getId(), product.getPlatformName(), price);
        kafkaTemplate.send("price-tracking-topic", priceMessage);
    }
}
