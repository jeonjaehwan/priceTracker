package com.pricewatcher.main_service.repository;

import com.pricewatcher.common_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email);
}
