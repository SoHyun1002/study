package com.study.backend.controller;

import com.study.backend.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 사용자 탈퇴 요청을 처리하는 컨트롤러입니다.
 *
 * 이 컨트롤러는 사용자가 계정 탈퇴를 요청할 수 있는 API를 제공합니다.
 * 탈퇴 요청이 접수되면 실제 데이터는 즉시 삭제되지 않고,
 * 30일 동안 보존되며 이 기간 동안은 사용자 정보 조회가 차단됩니다.
 */
@RestController
@RequestMapping("/users")
public class UserDeletionController {

    private final AccountService accountService;

    // 생성자 주입 방식으로 AccountService를 주입합니다.
    public UserDeletionController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 사용자 탈퇴 요청을 처리하는 엔드포인트입니다.
     *
     * 요청을 받은 사용자의 데이터는 즉시 삭제되지 않고, 30일 동안 유예 후 삭제됩니다.
     * 유예 기간 동안은 사용자 정보에 접근할 수 없습니다.
     *
     * @param uId 탈퇴 요청을 하는 사용자의 고유 ID
     * @return 200 OK 응답
     */
    @PostMapping("/delete/{uId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long uId) {
        accountService.requestAccountDeletion(uId);
        return ResponseEntity.ok().build();
    }

}
