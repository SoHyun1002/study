/**
 * 사용자 인증 관련 요청을 처리하는 컨트롤러입니다.
 *
 * 로그인, 리프레시 토큰을 통한 액세스 토큰 재발급, 로그아웃, 사용자 정보 조회 기능을 포함합니다.
 */
package com.study.backend.controller;

import com.study.backend.dto.LoginRequest;
import com.study.backend.entity.User;
import com.study.backend.service.AuthService;
import com.study.backend.service.UserCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserAuthController {

    private final AuthService authService;
    private final UserCacheService userCacheService;

    // 인증 서비스와 사용자 조회 서비스 주입
    public UserAuthController(AuthService authService, UserCacheService userCacheService) {
        this.authService = authService;
        this.userCacheService = userCacheService;
    }

    /**
     * 사용자 로그인 요청을 처리합니다.
     * 이메일과 비밀번호를 검증하고, 액세스 토큰 및 리프레시 토큰을 쿠키에 저장합니다.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse) {
        return authService.handleLogin(request, httpResponse);
    }

    /**
     * 리프레시 토큰을 사용해 새로운 액세스 토큰을 발급받는 요청을 처리합니다.
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {
        return authService.handleRefreshToken(refreshToken);
    }


    /**
     * 로그아웃 요청을 처리합니다.
     * 쿠키에서 액세스 토큰을 제거하여 클라이언트 측 인증 상태를 만료시킵니다.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse httpResponse) {
        authService.handleLogout(request, httpResponse);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 ID를 이용해 사용자 정보를 조회합니다.
     *
     * @param uId 사용자 ID
     * @return 사용자 정보
     */
    @GetMapping("/{uId}")
    public ResponseEntity<User> getUser(@PathVariable("uId") Long uId) {
        return ResponseEntity.ok(userCacheService.getUserById(uId));
    }

}
