package com.pricewatcher.price_storage_service.service;


import com.pricewatcher.common_service.dto.PriceMessageReq;
import com.pricewatcher.price_storage_service.entity.PriceHistory;
import com.pricewatcher.price_storage_service.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KafkaStorageConsumerService {

    private final PriceHistoryRepository priceHistoryRepository;

    @Transactional
    @KafkaListener(topics = "price-tracking-topic", groupId = "price-storage-group")
    public void consumePriceMessage(PriceMessageReq priceMessage) {
        try {
            PriceHistory priceHistory = PriceHistory.from(priceMessage);
            priceHistoryRepository.save(priceHistory);
            log.info("Price history stored for product ID {}: Price {}", priceMessage.getIdentifierValue(), priceMessage.getPrice());
        } catch (Exception e) {
            log.error("Error while storing price history for product ID {}: {}", priceMessage.getIdentifierValue(), e.getMessage());
        }
    }
}
