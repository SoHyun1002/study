package com.study.backend.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long uId;         // 유저 아이디

    @Column(name = "u_name")
    private String uName;       // 유저 이름

    @Column(name = "u_password")
    private String uPassword;   // 유저 패스워드

    @Column(name = "u_email")
    private String uEmail;      // 유저 이메일

    @Column(name = "u_role")
    private String uRole;       // 유저 권한


    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuRole() {
        return uRole;
    }

    public void setuRole(String uRole) {
        this.uRole = uRole;
    }
}
