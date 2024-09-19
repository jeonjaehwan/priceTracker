package com.pricewatcher.common_service.entity;

import com.pricewatcher.common_service.dto.ProductReq;
import com.pricewatcher.common_service.enums.Identifier;
import com.pricewatcher.common_service.enums.Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "platform_product")
public class PlatformProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform_name", nullable = false)
    private Platform platformName;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "target_price", nullable = false)
    private BigDecimal targetPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "identifier_type", nullable = false)
    private Identifier identifierType;

    @Column(name = "identifier_value", nullable = false)
    private String identifierValue;

    public void setProduct(Product product) {
        this.product = product;
    }

    public static PlatformProduct from(Product product, ProductReq productReq) {
        return PlatformProduct.builder()
                .product(product)
                .platformName(productReq.getPlatformName())
                .imageUrl(productReq.getImageUrl())
                .targetPrice(productReq.getTargetPrice())
                .identifierType(productReq.getIdentifierType())
                .identifierValue(productReq.getIdentifierValue())
                .build();
    }

}
