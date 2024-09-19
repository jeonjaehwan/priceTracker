package com.pricewatcher.price_tracker_service.dto;

import com.pricewatcher.common_service.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceMessageReq {

    private Long productId;
    private Platform platformName;
    private BigDecimal price;

}
