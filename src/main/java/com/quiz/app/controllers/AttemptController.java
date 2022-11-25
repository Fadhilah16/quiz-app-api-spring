package com.quiz.app.controllers;

import javax.servlet.http.HttpServletRequest;
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
import com.quiz.app.models.Attempt;
import com.quiz.app.services.AttemptService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/attempts")
@AllArgsConstructor
public class AttemptController {

    private AttemptService attemptService;

    @PostMapping
    public ResponseEntity<?> createAttempt(@Valid @RequestBody Attempt attempt, Errors errors, HttpServletRequest request){

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
           return ResponseEntity.ok(attemptService.createAttempt(attempt, request));
        } catch (Exception e) {
            throw new RuntimeException("Failed create attempt");
        }

    }   

    @PutMapping
    public ResponseEntity<?> updatAttempt(@Valid @RequestBody Attempt attempt, Errors errors){
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
            return ResponseEntity.ok(attemptService.updateAttempt(attempt));
         } catch (Exception e) {
             throw new RuntimeException("Failed create attempt");
         }
 
    }

    @GetMapping
    public Iterable<Attempt> findAllAttempts(){
        return attemptService.findAllAttempts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attempt> findAttempt(@PathVariable("id") Long id){
        return ResponseEntity.ok(attemptService.findAttemptById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAttempt(@PathVariable("id") Long id){
        attemptService.deleteAttemptById(id);
    }


}
