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
    private String alertMessage;

    public static AlertMessageReq from(Long productId, String productName, BigDecimal targetPrice, BigDecimal currentPrice) {
        String alertMessage = String.format(
                "상품 [%s]의 가격이 목표 가격(%.2f) 이하로 떨어졌습니다. 현재 가격: %.2f",
                productName, targetPrice, currentPrice
        );

        return AlertMessageReq.builder()
                .productId(productId)
                .productName(productName)
                .targetPrice(targetPrice)
                .currentPrice(currentPrice)
                .alertMessage(alertMessage)
                .build();
    }

}
