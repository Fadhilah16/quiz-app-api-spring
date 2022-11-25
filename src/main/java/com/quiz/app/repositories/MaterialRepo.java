package com.quiz.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.app.models.Material;
import com.quiz.app.models.Subject;

@Repository
public interface MaterialRepo extends JpaRepository<Material, Long> {
    
    public Long countBySubject(Subject subject);
    public List<Material> findBySubject(Subject subject);
}