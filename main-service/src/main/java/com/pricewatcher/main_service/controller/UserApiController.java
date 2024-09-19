package com.pricewatcher.main_service.controller;

import com.pricewatcher.common_service.dto.UserReq;
import com.pricewatcher.common_service.dto.UserRes;
import com.pricewatcher.main_service.security.CustomUserDetails;
import com.pricewatcher.main_service.security.CustomUserDetailsService;
import com.pricewatcher.main_service.service.UserService;
import com.pricewatcher.main_service.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private ResponseEntity<?> buildValidationErrorResponse(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorMessages);
    }
}
