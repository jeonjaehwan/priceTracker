package com.pricewatcher.main_service.entity;

import com.pricewatcher.main_service.dto.ProductReq;
import com.pricewatcher.main_service.enums.Identifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Table(name = "product")
public class Product extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatformProduct> platformProducts = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "identifier_type", nullable = false)
    private Identifier identifierType;

    @Column(name = "identifier_value", nullable = false)
    private String identifierValue;

    public void addPlatformProduct(PlatformProduct platformProduct) {
        this.platformProducts.add(platformProduct);
        platformProduct.setProduct(this);
    }

    public static Product from(User user, ProductReq productReq) {
        Product product = Product.builder()
                .user(user)
                .name(productReq.getProductName())
                .identifierType(productReq.getIdentifierType())
                .identifierValue(productReq.getIdentifierValue())
                .build();
        PlatformProduct platformProduct = PlatformProduct.from(product, productReq);
        product.addPlatformProduct(platformProduct);
        return product;
    }
}
