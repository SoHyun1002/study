package com.study.backend.controller;

import com.study.backend.dto.LoginRequest;
import com.study.backend.entity.user.User;
import com.study.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserAuthController {

    private final UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse) {
        // 검증 로직
        java.util.Optional<User> userOptional = userService.findByuEmail(request.getuEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email"));
        }
        User user = userOptional.get();
        if (!user.getuPassword().equals(request.getuPassword())) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid password"));
        }
        // 쿠키
        String token = userService.login(user.getuEmail(), user.getuPassword());
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60); // 1 hour
        httpResponse.addCookie(jwtCookie);
        return ResponseEntity.ok(Map.of("token", token));
    }



    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse httpResponse) {
        String token = userService.resolveToken(request);
        System.out.println("Resolved token: " + token);

        // 쿠키 제거: 유효시간 0으로 설정
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // 즉시 만료
        httpResponse.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }




    // 유저 조회
    @GetMapping("/{uId}")
    public ResponseEntity<User> getUser(@PathVariable("uId") Long uId) {
        return ResponseEntity.ok(userService.getUserById(uId));
    }


}
