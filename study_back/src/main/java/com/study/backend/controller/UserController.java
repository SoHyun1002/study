package com.study.backend.controller;

import com.study.backend.entity.user.User;
import com.study.backend.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        String token = userService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // 더미 유저 조회
    @GetMapping("/dummy")
    public ResponseEntity<User> getDummyUser() {
        User user = userService.getDummyUser("user:1");
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}