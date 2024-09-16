package com.pricewatcher.main_service.entity;

import com.pricewatcher.main_service.dto.ProductReq;
import com.pricewatcher.main_service.enums.Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "platform_product")
public class PlatformProduct extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setProduct(Product product) {
        this.product = product;
    }

    public static PlatformProduct from(Product product, ProductReq productReq) {
        return PlatformProduct.builder()
                .product(product)
                .platformName(productReq.getPlatformName())
                .imageUrl(productReq.getImageUrl())
                .targetPrice(productReq.getTargetPrice())
                .build();
    }

}
