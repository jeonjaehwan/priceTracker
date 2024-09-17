package com.pricewatcher.common_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRes {

    private BigDecimal value;
    private String currency;
}