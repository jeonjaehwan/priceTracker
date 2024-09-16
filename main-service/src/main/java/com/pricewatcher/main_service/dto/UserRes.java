package com.pricewatcher.main_service.dto;

import com.pricewatcher.main_service.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {

    private String name;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    public static UserRes from(User user) {
        return UserRes.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
