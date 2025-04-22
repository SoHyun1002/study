package com.study.backend.service;

import com.study.backend.component.JwtToken;
import com.study.backend.entity.user.User;
import com.study.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final JwtToken jwtToken;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;

    // 생성자에 추가
    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate, JwtToken jwtToken, StringRedisTemplate stringRedisTemplate, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtToken = jwtToken;
        this.stringRedisTemplate = stringRedisTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setuRole("USER");
        user.setuPassword(passwordEncoder.encode(user.getuPassword()));

        // 1. 유저 저장
        User saved = userRepository.save(user);

        // 2. JWT 토큰 생성
        String token = jwtToken.generateToken(saved.getuEmail());

        // 3. Redis에 저장
        String tokenKey = "user:token:" + saved.getuId();
        stringRedisTemplate.opsForValue().set(tokenKey, token);

        System.out.println("Inserted JWT token into Redis with key: " + tokenKey);

        return saved;
    }

    public String login(String email, String password) {
        User user = userRepository.findByuEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getuPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtToken.generateToken(user.getuEmail());
    }

    public User getUserById(Long uId) {
        User cached = redisTemplate.opsForValue().get("user:" + uId);
        if (cached != null) return cached;
        return userRepository.findById(uId).orElseThrow();
    }

    public User updateUser(Long uId, User updatedUser) {
        User user = userRepository.findById(uId).orElseThrow();
        user.setuName(updatedUser.getuName());
        user.setuEmail(updatedUser.getuEmail());
        userRepository.save(user);
        redisTemplate.opsForValue().set("user:" + uId, user);
        return user;
    }

    public void deleteUser(Long uId) {
        userRepository.deleteById(uId);
        redisTemplate.delete("user:" + uId);
    }

    @PostConstruct
    public void init() {
        // 애플리케이션 시작 시 더미 데이터를 Redis에 추가
        createDummyData();
    }

    // 레디스 테스트용 더미 데이터
    public void createDummyData() {
        User user = new User();
        user.setuEmail("testuser@example.com");
        user.setuPassword("{noop}password123");
        user.setuName("test");
        user.setuRole("USER");

        User saved = userRepository.save(user);

        // JWT 토큰 생성
        String token = jwtToken.generateToken(saved.getuEmail());

        // Redis에 토큰 저장
        String tokenKey = "user:token:" + saved.getuId();

        stringRedisTemplate.opsForValue().set(token, token);
        System.out.println("Inserted dummy JWT token into Redis with key: " + tokenKey);
    }

    // 더미 유저 조회
    public User getDummyUser(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }
        return null;
    }
}