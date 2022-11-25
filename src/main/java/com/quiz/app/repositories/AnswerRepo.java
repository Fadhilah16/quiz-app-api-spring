package com.quiz.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.app.models.Answer;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    
}