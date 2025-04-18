package com.study.backend.service;

import com.study.backend.component.JwtToken;
import com.study.backend.entity.user.User;
import com.study.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final JwtToken jwtToken;

    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate, JwtToken jwtToken) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtToken = jwtToken;
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
        // 더미 유저 엔티티 생성
        User user = new User();
        user.setuId(1L);
        user.setuName("DummyUser");
        user.setuPassword("dummyPassword");
        user.setuEmail("dummy@example.com");
        user.setuRole("ROLE_USER");

        // Redis에 저장할 때 사용할 키
        String redisKey = "user:" + user.getuId();

        // Redis에 저장
        redisTemplate.opsForValue().set(redisKey, user);

        System.out.println("Inserted dummy User into Redis with key: " + redisKey);
    }

    // 더미 유저 조회
    public User getDummyUser(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}