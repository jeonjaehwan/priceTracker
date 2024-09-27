package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthReq {

    private String id;
    private String username;
    private String email;
    private String phoneNumber;
}
