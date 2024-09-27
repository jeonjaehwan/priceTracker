package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {

    private Long userId;

    private String name;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    public static UserRes from(User user) {
        return UserRes.builder()
                .userId(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
