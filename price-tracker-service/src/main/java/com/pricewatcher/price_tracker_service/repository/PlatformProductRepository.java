package com.pricewatcher.price_tracker_service.repository;

import com.pricewatcher.common_service.entity.PlatformProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformProductRepository extends JpaRepository<PlatformProduct, Long> {
}
