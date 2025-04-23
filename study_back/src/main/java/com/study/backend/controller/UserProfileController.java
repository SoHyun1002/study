package com.study.backend.controller;

import com.study.backend.entity.User;
import com.study.backend.service.UserCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 프로필 관련 기능을 담당하는 컨트롤러입니다.
 *
 * 이 컨트롤러는 사용자 정보를 수정하는 기능을 제공합니다.
 * UserCacheService를 통해 사용자 정보를 캐시 또는 DB에서 가져와 갱신합니다.
 */
@RestController
@RequestMapping("/users")
public class UserProfileController {

    private final UserCacheService userCacheService;

    // 생성자 주입 방식으로 사용자 캐시 서비스(UserCacheService)를 주입합니다.
    public UserProfileController(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    /**
     * 사용자 정보를 수정하는 엔드포인트입니다.
     *
     * @param uId 수정할 사용자 ID
     * @param updatedUser 수정된 사용자 정보가 담긴 객체
     * @return 수정된 사용자 정보
     */
    @PostMapping("/update/{uId}")
    public ResponseEntity<User> updateUser(@PathVariable Long uId, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userCacheService.updateUser(uId, updatedUser));
    }
}
