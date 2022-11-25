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
import com.quiz.app.models.Question;
import com.quiz.app.services.QuestionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/questions")
@AllArgsConstructor
public class QuestionController {
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Question question, Errors errors){
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
            return ResponseEntity.ok(questionService.createQuestion(question)) ;
        } catch (Exception e) {
            throw new RuntimeException("Failed create question");
        }
    }

    @PutMapping
    public ResponseEntity<?> updatQuestion(@Valid @RequestBody Question question, Errors errors){
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
            return ResponseEntity.ok(questionService.updateQuestion(question));
        } catch (Exception e) {
            throw new RuntimeException("Failed upadte question");
        }
    }

    @GetMapping
    public Iterable<Question> findAllQuestions(){
        return questionService.findAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> findQuestion(@PathVariable("id") Long id){
        return ResponseEntity.ok(questionService.findQuestionById(id));
    }

    @DeleteMapping("/{id}")
        public void deleteQuestion(@PathVariable("id") Long id){
            questionService.deleteQuestionById(id);
        }
}
