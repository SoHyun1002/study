package com.study.backend.service;

import com.study.backend.component.JwtToken;
import com.study.backend.entity.user.User;
import com.study.backend.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final JwtToken jwtToken;
    private final StringRedisTemplate stringRedisTemplate;

    // 생성자에 추가
    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate, JwtToken jwtToken, StringRedisTemplate stringRedisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtToken = jwtToken;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public User registerUser(User user) {
        user.setuRole("USER");
        user.setuPassword("{noop}" + user.getuPassword()); // 테스트용, 실제로는 BCrypt
        User saved = userRepository.save(user);
        redisTemplate.opsForValue().set("user:" + saved.getuId(), saved);
        return saved;
    }

    public String login(String email, String password) {
        User user = userRepository.findByuEmail(email).orElseThrow();
        if (user.getuPassword().equals("{noop}" + password)) {
            return jwtToken.generateToken(email);
        }
        throw new RuntimeException("Invalid credentials");
    }

    public User getUserById(Long id) {
        User cached = (User) redisTemplate.opsForValue().get("user:" + id);
        if (cached != null) return cached;
        return userRepository.findById(id).orElseThrow();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setuName(updatedUser.getuName());
        user.setuEmail(updatedUser.getuEmail());
        userRepository.save(user);
        redisTemplate.opsForValue().set("user:" + id, user);
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        redisTemplate.delete("user:" + id);
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
}