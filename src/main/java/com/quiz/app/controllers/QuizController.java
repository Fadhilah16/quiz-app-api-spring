package com.quiz.app.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.app.dtos.ResponseDto;
import com.quiz.app.models.Quiz;
import com.quiz.app.services.QuizService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/quizzes")
@AllArgsConstructor
public class QuizController {
    private QuizService quizService;
    
    @PostMapping
    public ResponseEntity<?> createQuiz(@Valid @RequestBody Quiz quiz, Errors errors){
        try {
            ResponseDto response = new ResponseDto();
            if(errors.hasErrors()){
                for (ObjectError error : errors.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                    response.getMessages().add(error.getDefaultMessage());
                }
                response.setStatus("400");
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.ok(quizService.createQuiz(quiz));
        } catch (Exception e) {
            throw new RuntimeException("Failed create Quiz");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateQuiz(@Valid @RequestBody Quiz quiz, Errors errors){
        try {
            ResponseDto response = new ResponseDto();
            if(errors.hasErrors()){
                for (ObjectError error : errors.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                    response.getMessages().add(error.getDefaultMessage());
                }
                response.setStatus("400");
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.ok(quizService.updateQuiz(quiz));
        } catch (Exception e) {
            throw new RuntimeException("Failed update Quiz");
        }
    }

    @GetMapping
    public Iterable<Quiz> findAllQuizzes(){
        return quizService.findAllQuizzes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable("id") Long id){
        return ResponseEntity.ok(quizService.findQuizById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable("id") Long id){
        quizService.deleteQuizById(id);
    }
}
