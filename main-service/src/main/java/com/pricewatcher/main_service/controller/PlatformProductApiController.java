package com.pricewatcher.main_service.controller;

import com.pricewatcher.common_service.dto.PlatformProductRes;
import com.pricewatcher.common_service.service.EbayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/platform-products")
@Slf4j
public class PlatformProductApiController {

    private final EbayService ebayService;

    @GetMapping("/search")
    public ResponseEntity<List<PlatformProductRes>> searchProductsPrice(@RequestParam String productName) {
        List<PlatformProductRes> prices = ebayService.searchProductPrice(productName);

        return ResponseEntity.ok(prices);
    }
}
