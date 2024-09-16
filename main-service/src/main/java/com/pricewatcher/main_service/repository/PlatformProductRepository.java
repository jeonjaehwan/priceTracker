package com.pricewatcher.main_service.repository;

import com.pricewatcher.main_service.entity.PlatformProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformProductRepository extends JpaRepository<PlatformProduct, Long> {

}
