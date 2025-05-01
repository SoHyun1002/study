package com.study.backend.controller;

import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {

    public String setCookie(HttpServletResponse response) {
        // ResponseCookie 생성
        ResponseCookie cookie = ResponseCookie.from("cookieName", "cookieValue")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader("Set-Cookie", cookie.toString());

        return "success";
    }
}