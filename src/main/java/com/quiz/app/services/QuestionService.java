package com.quiz.app.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.quiz.app.models.Material;
import com.quiz.app.models.Question;
import com.quiz.app.repositories.MaterialRepo;
import com.quiz.app.repositories.QuestionRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class QuestionService {
    private QuestionRepo questionRepo;
    private MaterialRepo materialRepo;

    public Question createQuestion(Question question){
        Optional<Material> materialOptional = materialRepo.findById(question.getMaterial().getId());
        question.setMaterial(materialOptional.get());
        question.setCreatedAt(new Date());
        question.setUpdatedAt(new Date());
        return questionRepo.save(question);
    }

    public Question updateQuestion(Question question){
        question.setUpdatedAt(new Date());
        return questionRepo.save(question);
    }

    public Iterable<Question> findAllQuestions(){
        return questionRepo.findAll();
    }

    public Question findQuestionById(Long id){
        Optional<Question> questionOptional = questionRepo.findById(id);

        if(questionOptional.isPresent()){
            return questionOptional.get();
        }
        return null;
    }

    public void deleteQuestionById(Long id){
        questionRepo.deleteById(id);
    }
}
