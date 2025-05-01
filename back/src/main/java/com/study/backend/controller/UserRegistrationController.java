package com.study.backend.controller;

import com.study.backend.entity.User;
import com.study.backend.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 회원가입을 처리하는 컨트롤러입니다.
 *
 * 사용자가 회원가입을 요청할 때, 해당 요청을 AccountService로 전달하여 사용자 정보를 저장합니다.
 */
@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final AccountService accountService;

    // AccountService를 주입받아 사용자 등록 요청을 처리합니다.
    public UserRegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 회원가입 요청을 처리하는 엔드포인트입니다.
     *
     * 클라이언트로부터 사용자 정보를 받아 AccountService를 통해 DB에 저장합니다.
     *
     * @param user 요청 본문에 포함된 사용자 정보
     * @return 저장된 사용자 정보를 포함한 응답
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(accountService.registerUser(user));
    }

}
