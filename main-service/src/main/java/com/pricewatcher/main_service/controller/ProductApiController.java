package com.pricewatcher.main_service.controller;

import com.pricewatcher.main_service.dto.ProductReq;
import com.pricewatcher.main_service.security.CustomUserDetails;
import com.pricewatcher.main_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody ProductReq productReq) {
        productService.saveProduct(userDetails.getId(), productReq);

        return ResponseEntity.ok().build();
    }
}
