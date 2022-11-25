package com.quiz.app.repositories;

import com.quiz.app.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    public Boolean existsByUsername(String username);
}