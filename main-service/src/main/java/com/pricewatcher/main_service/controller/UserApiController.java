package com.pricewatcher.main_service.controller;

import com.pricewatcher.common_service.dto.OAuthReq;
import com.pricewatcher.common_service.dto.UserReq;
import com.pricewatcher.common_service.dto.UserRes;
import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.common_service.enums.Role;
import com.pricewatcher.main_service.dto.*;
import com.pricewatcher.main_service.security.CustomUserDetails;
import com.pricewatcher.main_service.security.CustomUserDetailsService;
import com.pricewatcher.main_service.service.UserService;
import com.pricewatcher.main_service.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().body("로그아웃 되었습니댜ㅏ.");
    }

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserReq userReq,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        UserRes userRes = userService.createUser(userReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRes);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getId());

        return ResponseEntity.ok("User Deleted successfully");
    }

    /**
     * 인증번호 전송
     */
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@Validated @RequestBody VerifyCodeReq dto) {
        userService.sendSmsToFindEmail(dto);

        return ResponseEntity.ok("SMS가 성공적으로 전송되었습니다.");
    }

    /**
     * 인증번호 확인 -> 아이디 찾기
     */
    @PostMapping("/find-username")
    public ResponseEntity<Object> verifyCodeAndFindUsername(@Valid @RequestBody CheckCodeReq checkCodeReq) {
        userService.verifySms(checkCodeReq);
        String username = userService.getUsername(checkCodeReq.getName(), checkCodeReq.getPhoneNumber());

        FindUsernameRes response = new FindUsernameRes("아이디는 " + username + "입니다.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping("/find-password")
    public ResponseEntity<Object> resetPassword(@Validated @RequestBody FindPasswordReq FindPasswordReq) {
        UserRes userRes = userService.findUserForPasswordReset(FindPasswordReq.getName(), FindPasswordReq.getUsername(), FindPasswordReq.getEmail());
        String temporaryPassword = PasswordGenerator.generatePassword(10);
        userService.updatePassword(userRes.getUserId(), temporaryPassword);
        FindPasswordRes response = new FindPasswordRes("임시 비밀번호는 " + temporaryPassword + "입니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * refreshToken 갱신
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token provided");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok("{\"accessToken\": \"" + newAccessToken + "\"}");
    }

    /**
     * 구글 소셜 로그인
     */
    @PostMapping("/google-login")
    public ResponseEntity<?> loginWithSocial(@RequestBody OAuthReq oAuthReq) {
        return handleSocialLogin(oAuthReq);
    }

    /**
     * 카카오 소셜 로그인
     */
    @PostMapping("/kakao-login")
    public ResponseEntity<?> loginWithKakao(@RequestBody OAuthReq oAuthReq) {
        return handleSocialLogin(oAuthReq);
    }

    private ResponseEntity<?> handleSocialLogin(OAuthReq oAuthReq) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(oAuthReq.getUsername());
            User user = userDetails.getUser();

            if (user.getRole() == Role.SOCIAL) {
                return generateJwtRes(user.getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다. 일반 로그인을 사용하세요.");
            }
        } catch (UsernameNotFoundException e) {
            UserRes newUserResponse = userService.createUser(UserReq.from(oAuthReq));
            return generateJwtRes(newUserResponse.getUsername());
        }
    }


    private ResponseEntity<?> buildValidationErrorResponse(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorMessages);
    }

    private ResponseEntity<JwtRes> generateJwtRes(String username) {
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        JwtRes jwtResponse = JwtRes.from(accessToken, refreshToken, username);
        return ResponseEntity.ok(jwtResponse);
    }
}
