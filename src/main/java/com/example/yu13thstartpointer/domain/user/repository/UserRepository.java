package com.example.yu13thstartpointer.domain.user.repository;

import com.example.yu13thstartpointer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Long> {

    /** 이메일로 조회 */
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);

    /** 이메일 중복 여부 */
    boolean existsByEmail(String email);

    /** 닉네임 중복 여부 */
    boolean existsByUserId(String userId);
}
