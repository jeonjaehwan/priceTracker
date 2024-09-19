package com.pricewatcher.main_service.repository;

import com.pricewatcher.common_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
