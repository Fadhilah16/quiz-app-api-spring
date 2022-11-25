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
import com.quiz.app.models.Answer;
import com.quiz.app.services.AnswerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/answers")
@AllArgsConstructor
public class AnswerController {
    private AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> createAnswer(@Valid @RequestBody Answer answer, Errors errors){
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
            return ResponseEntity.ok(answerService.createAnswer(answer));
        } catch (Exception e) {
            throw new RuntimeException("Failed create answer");
        }
    }

    @PutMapping
    public ResponseEntity<?> updatAnswer(@Valid @RequestBody Answer answer, Errors errors){
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
            return ResponseEntity.ok(answerService.updateAnswer(answer));
        } catch (Exception e) {
            throw new RuntimeException("Failed update answer");
        }
    }

    @GetMapping
    public Iterable<Answer> findAllAnswers(){
        return answerService.findAllAnswers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> findAnswer(@PathVariable("id") Long id){
        return ResponseEntity.ok(answerService.findAnswerById(id));
    }

    @DeleteMapping("/{id}")
        public void deleteAnswer(@PathVariable("id") Long id){
            answerService.deleteAnswerById(id);
        }
}
