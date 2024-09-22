package com.pricewatcher.price_storage_service.repository;

import com.pricewatcher.price_storage_service.entity.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String> {
}
