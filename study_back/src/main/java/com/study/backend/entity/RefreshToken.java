package com.study.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RefreshToken {

    @Id
    private String token;
    private Long uId;
    private LocalDateTime expiryDate;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public String getToken() {
        return token;
    }

    public Long getuId() {
        return uId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
