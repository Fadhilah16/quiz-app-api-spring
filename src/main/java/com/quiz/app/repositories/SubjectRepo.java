package com.quiz.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.app.models.Subject;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {

    
}
