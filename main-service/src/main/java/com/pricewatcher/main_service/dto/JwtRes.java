package com.pricewatcher.main_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class JwtRes {
    private String accessToken;
    private String refreshToken;
    private String username;
    private Long expiresIn;
    private Long refreshExpiresIn;

    public static JwtRes from(String accessToken, String refreshToken, String username, Long expiresIn, Long refreshExpiresIn) {
        return JwtRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(username)
                .expiresIn(expiresIn)
                .refreshExpiresIn(refreshExpiresIn)
                .build();

    }
}