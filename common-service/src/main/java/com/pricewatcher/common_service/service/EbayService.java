package com.pricewatcher.common_service.service;

import com.pricewatcher.common_service.dto.EbaySearchRes;
import com.pricewatcher.common_service.dto.PlatformProductRes;
import com.pricewatcher.common_service.enums.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EbayService {

    private final OAuthService oAuthService;
    private final RestTemplate restTemplate;

    public List<PlatformProductRes> searchProductPrice(String productName) {

        String accessToken = oAuthService.getAccessToken();

        if (accessToken == null) {
            throw new IllegalStateException("Could not retrieve access token");
        }

        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + productName + "&limit=5";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<EbaySearchRes> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<PlatformProductRes> items = response.getBody().getItemSummaries();

            return items.stream()
                    .peek(item -> item.setPlatformName(Platform.EBAY))
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    public BigDecimal getPrice(String identifierValue) {
        String accessToken = oAuthService.getAccessToken();

        if (accessToken == null) {
            throw new IllegalStateException("Could not retrieve access token");
        }

        String url = "https://api.ebay.com/buy/browse/v1/item/" + identifierValue;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PlatformProductRes> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            PlatformProductRes product = response.getBody();

            if (product.getPrice() != null) {
                return product.getPrice().getValue();  // BigDecimal 값을 반환
            }
        }

        return BigDecimal.ZERO;  // 가격 정보가 없을 경우 0 반환
    }

}