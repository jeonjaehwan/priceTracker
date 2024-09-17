package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.enums.Platform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformProductRes {

    private String title;
    private ImageRes image;
    private Platform platformName;
    private PriceRes price;
    private String itemId;
}
