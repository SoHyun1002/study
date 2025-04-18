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

    public String generateToken(String email) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @PostConstruct
    public void printDefaultToken() {
        String token = generateToken("testuser@example.com");
        System.out.println("🔐 Default JWT for testuser@example.com:\n" + token);
    }


}
