package com.study.backend.controller;

import com.study.backend.dto.LoginRequest;
import com.study.backend.entity.user.User;
import com.study.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getuEmail(), request.getuPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    // 유저 조회
    @GetMapping("/{uId}")
    public ResponseEntity<User> getUser(@PathVariable("uId") Long uId) {
        return ResponseEntity.ok(userService.getUserById(uId));
    }

    // 유저 수정
    @PostMapping("/update/{uId}")
    public ResponseEntity<User> updateUser(@PathVariable Long uId, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(uId, updatedUser));
    }

    // 유저 삭제
    @PostMapping("/delete/{uId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long uId) {
        userService.deleteUser(uId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        String resolveToken;
        return ResponseEntity.ok().build();
    }


}