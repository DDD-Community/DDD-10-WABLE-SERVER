package com.wable.user_api.domain.user.repository;

import com.wable.user_api.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByUserId(String userId);

    Optional<Users> findByUserId(String userId);

}
