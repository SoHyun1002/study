package com.study.backend.controller;

import com.study.backend.entity.user.User;
import com.study.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    // 유저 수정
    @PostMapping("/update/{uId}")
    public ResponseEntity<User> updateUser(@PathVariable Long uId, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(uId, updatedUser));
    }
}
