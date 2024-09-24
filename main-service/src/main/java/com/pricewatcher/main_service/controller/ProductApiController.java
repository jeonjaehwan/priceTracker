package com.pricewatcher.main_service.controller;

import com.pricewatcher.common_service.dto.ProductReq;
import com.pricewatcher.common_service.dto.ProductRes;
import com.pricewatcher.common_service.service.EbayService;
import com.pricewatcher.main_service.security.CustomUserDetails;
import com.pricewatcher.main_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductApiController {

    private final ProductService productService;
    private final EbayService ebayService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody ProductReq productReq) {
        productService.saveProduct(userDetails.getId(), productReq);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductRes>> searchProductsPrice(@RequestParam String productName) {
        List<ProductRes> prices = ebayService.searchProductPrice(productName);

        return ResponseEntity.ok(prices);
    }
}
