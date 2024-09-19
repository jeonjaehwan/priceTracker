package com.pricewatcher.main_service.service;

import com.pricewatcher.common_service.dto.ProductReq;
import com.pricewatcher.common_service.entity.Product;
import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.main_service.exception.UserNotFoundException;
import com.pricewatcher.main_service.repository.ProductRepository;
import com.pricewatcher.main_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveProduct(Long userId, ProductReq productReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Product product = Product.from(user, productReq);
        productRepository.save(product);
    }

}
