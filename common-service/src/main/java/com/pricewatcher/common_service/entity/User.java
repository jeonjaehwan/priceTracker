package com.pricewatcher.common_service.entity;

import com.pricewatcher.common_service.dto.UserReq;
import com.pricewatcher.common_service.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private Role role;

    public void updatePassword(String password) {
        this.password = password;
    }

    public static User from(UserReq userReq) {
        return User.builder()
                .name(userReq.getName())
                .username(userReq.getUsername())
                .password(userReq.getPassword())
                .email(userReq.getEmail())
                .phoneNumber(userReq.getPhoneNumber())
                .role(Role.USER)
                .build();
    }
}
