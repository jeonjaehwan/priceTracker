package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.entity.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceMessageReq {

    private String identifierValue;
    private BigDecimal price;
    private LocalDateTime trackedDate;

    public static PriceMessageReq from(Product product, BigDecimal price) {
        return PriceMessageReq.builder()
                .identifierValue(product.getIdentifierValue())
                .price(price)
                .trackedDate(LocalDateTime.now())
                .build();
    }
}
