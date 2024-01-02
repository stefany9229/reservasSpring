package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

}
