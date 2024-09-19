package com.bank.service.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.service.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"userProfile"})
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}