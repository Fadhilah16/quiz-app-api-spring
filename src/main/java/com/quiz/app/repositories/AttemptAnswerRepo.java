package com.quiz.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.app.models.Attempt;
import com.quiz.app.models.AttemptAnswer;

public interface AttemptAnswerRepo extends JpaRepository<AttemptAnswer, Long> {
    List<AttemptAnswer> findByAttempt(Attempt attempt);
}
