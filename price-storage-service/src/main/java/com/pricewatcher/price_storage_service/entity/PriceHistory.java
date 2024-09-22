package com.pricewatcher.price_storage_service.entity;

import com.pricewatcher.common_service.dto.PriceMessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "price_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {

    @Id
    private String id;

    private String identifierValue;

    private BigDecimal price;

    private LocalDateTime trackedDate;

    public static PriceHistory from(PriceMessageReq priceMessageReq) {
        return PriceHistory.builder()
                .identifierValue(priceMessageReq.getIdentifierValue())
                .price(priceMessageReq.getPrice())
                .trackedDate(priceMessageReq.getTrackedDate())
                .build();
    }

}
