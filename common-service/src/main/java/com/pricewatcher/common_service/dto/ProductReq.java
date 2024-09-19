package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.enums.Identifier;
import com.pricewatcher.common_service.enums.Platform;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReq {

    private String productName;
    private Identifier identifierType;
    private String identifierValue;
    private Platform platformName;
    private String imageUrl;
    private BigDecimal targetPrice;
}
