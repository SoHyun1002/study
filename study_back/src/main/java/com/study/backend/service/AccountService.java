package com.study.backend.service;


import com.study.backend.component.JwtToken;
import com.study.backend.entity.User;
import com.study.backend.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, User> redisTemplate;
    private final JwtToken jwtToken;

    /**
     * 인증과 관련된 비즈니스 로직을 처리하는 서비스 클래스입니다.
     *
     * 로그인, 비밀번호 검증, 토큰 발급 및 재발급 기능을 포함합니다.
     */

    public AccountService(UserRepository userRepository,
                          StringRedisTemplate stringRedisTemplate,
                          PasswordEncoder passwordEncoder,
                          RedisTemplate<String, User> redisTemplate,
                          JwtToken jwtToken) {
        this.userRepository = userRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
        this.jwtToken = jwtToken;
    }

    /**
     * 사용자 회원가입을 처리합니다.
     * 비밀번호를 암호화하고, 기본 권한을 설정한 후 유저 정보를 저장합니다.
     * JWT 토큰을 발급하여 Redis에 저장합니다.
     */
    public User registerUser(User user) {
        user.setuRole("USER");
        user.setuPassword(passwordEncoder.encode(user.getuPassword()));

        User saved = userRepository.save(user);

        String token = jwtToken.generateToken(saved.getuEmail());
        String tokenKey = "user:token:" + saved.getuId();
        stringRedisTemplate.opsForValue().set(tokenKey, token);

        System.out.println("Inserted JWT token into Redis with key: " + tokenKey);

        return saved;
    }

    /**
     * 계정 삭제 요청을 처리합니다.
     * 사용자의 삭제 일시를 기록하고, 캐시(Redis)에서 해당 사용자 정보를 제거합니다.
     */
    public void requestAccountDeletion(Long uId) {
        User user = userRepository.findById(uId).orElseThrow();
        user.setDeletedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        redisTemplate.delete("user:" + uId);
    }
}