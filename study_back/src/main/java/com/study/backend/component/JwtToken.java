package com.study.backend.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.stereotype.Component;

import java.util.Date;
import jakarta.annotation.PostConstruct;

@Component
public class JwtToken {
    private final String SECRET_KEY = "dGhpc19pc19hX3Zlcnlfc2VjdXJlX3Rlc3Rfc2VjcmV0X2tleQ=="; // Base64 encoded

    // Access Token을 생성하는 메서드
    // 사용자 이메일을 기반으로 JWT를 생성하고, 5시간 동안 유효하게 설정함
    public String generateToken(String email) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5*60*60*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 사용자 이메일(Subject)을 추출하는 메서드
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 애플리케이션 시작 시 테스트용 기본 JWT 토큰을 생성하고 출력하는 메서드
    @PostConstruct
    public void printDefaultToken() {
        String token = generateToken("testuser@example.com");
        System.out.println("🔐 Default JWT for testuser@example.com:\n" + token);
    }

    // Refresh Token을 생성하는 메서드
    // 7일 동안 유효하며, 사용자 이메일을 기반으로 JWT를 생성함
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7일
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    // 토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 이메일을 추출하는 메서드
    public String getUserEmail(String token) {
        return extractEmail(token);
    }
}
