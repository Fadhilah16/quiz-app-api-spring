package com.quiz.app.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.quiz.app.models.Answer;
import com.quiz.app.models.Question;
import com.quiz.app.repositories.AnswerRepo;
import com.quiz.app.repositories.QuestionRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AnswerService {
    private AnswerRepo answerRepo;
    private QuestionRepo questionRepo;

    public Answer createAnswer(Answer answer){
        Optional<Question> questionOptional = questionRepo.findById(answer.getQuestion().getId());
        answer.setQuestion(questionOptional.get());
        answer.setCreatedAt(new Date());
        answer.setUpdatedAt(new Date());
        return answerRepo.save(answer);
    }

    public Answer updateAnswer(Answer answer){
        answer.setUpdatedAt(new Date());
        return answerRepo.save(answer);
    }

    public Iterable<Answer> findAllAnswers(){
        return answerRepo.findAll();
    }

    public Answer findAnswerById(Long id){
        Optional<Answer> answerOptional = answerRepo.findById(id);

        if(answerOptional.isPresent()){
            return answerOptional.get();
        }
        return null;
    }

    public void deleteAnswerById(Long id){
        answerRepo.deleteById(id);
    }
}
