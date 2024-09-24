package com.pricewatcher.price_tracker_service.repository;

import com.pricewatcher.common_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformProductRepository extends JpaRepository<Product, Long> {
}
