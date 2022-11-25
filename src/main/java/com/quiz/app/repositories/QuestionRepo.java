package com.quiz.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quiz.app.models.Material;
import com.quiz.app.models.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    
    public List<Question> findByMaterial(Material material);
    
    @Query(nativeQuery=true, value="SELECT *  FROM Question WHERE :material ORDER BY random() LIMIT :ratio")
    public List<Question> findRandomQuestionByMaterial(@Param("material") Material material, @Param("ratio") long ratio);

}