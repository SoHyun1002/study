package com.study.backend.controller;

import com.study.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserDeletionController {

    private final UserService userService;

    public UserDeletionController(UserService userService) {
        this.userService = userService;
    }

    // 유저 삭제
    @PostMapping("/delete/{uId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long uId) {
        userService.deleteUser(uId);
        return ResponseEntity.ok().build();
    }

}
