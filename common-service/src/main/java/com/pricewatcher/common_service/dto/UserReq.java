package com.pricewatcher.common_service.dto;

import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.common_service.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReq {

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자 사이로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자 모두 포함해주세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[0-9]{7,8}$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    private Role role;

    public static UserReq from(OAuthReq oAuthReq) {
        return UserReq.builder()
                .name(oAuthReq.getUsername())
                .username(oAuthReq.getId())
                .email(oAuthReq.getEmail())
                .phoneNumber(oAuthReq.getPhoneNumber())
                .role(Role.SOCIAL)
                .password("")
                .build();
    }
}
