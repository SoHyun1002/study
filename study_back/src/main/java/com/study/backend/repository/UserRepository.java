package com.study.backend.repository;

import com.study.backend.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByuEmail(String uEmail);
}
