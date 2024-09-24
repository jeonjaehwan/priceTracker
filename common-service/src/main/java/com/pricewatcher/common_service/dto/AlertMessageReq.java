package com.pricewatcher.common_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertMessageReq {

    private Long productId;
    private String productName;
    private BigDecimal targetPrice;
    private BigDecimal currentPrice;
    private Long userId;

    public static AlertMessageReq from(Long productId, String productName, BigDecimal targetPrice, BigDecimal currentPrice, Long userId) {
        return AlertMessageReq.builder()
                .productId(productId)
                .productName(productName)
                .targetPrice(targetPrice)
                .currentPrice(currentPrice)
                .userId(userId)
                .build();
    }

}
