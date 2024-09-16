package com.pricewatcher.main_service.dto;

import com.pricewatcher.main_service.enums.Platform;
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
