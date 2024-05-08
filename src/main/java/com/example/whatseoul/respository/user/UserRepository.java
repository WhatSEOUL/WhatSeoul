package com.example.whatseoul.respository.user;

import com.example.whatseoul.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUserName(String userName);
    Optional<User> findByUserEmail(String email); // 이메일을 기준으로 사용자 찾기
}
