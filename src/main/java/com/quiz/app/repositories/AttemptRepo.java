package com.quiz.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.app.models.Attempt;
import com.quiz.app.models.User;

public interface AttemptRepo extends JpaRepository<Attempt, Long> {
    public List<Attempt> findByUser(User user);
}
