package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findUsersWithOnlyRole(ERole role);

}
