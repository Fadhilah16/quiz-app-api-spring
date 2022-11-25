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
import com.quiz.app.models.Subject;
import com.quiz.app.services.SubjectService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/subjects")
@AllArgsConstructor
public class SubjectController {
    
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@Valid @RequestBody Subject subject, Errors errors){

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
           return ResponseEntity.ok(subjectService.createSubject(subject));
        } catch (Exception e) {
            throw new RuntimeException("Failed create subject");
        }

    }   

    @PutMapping
    public ResponseEntity<?> updatSubject(@Valid @RequestBody Subject subject, Errors errors){
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
            return ResponseEntity.ok(subjectService.updateSubject(subject));
         } catch (Exception e) {
             throw new RuntimeException("Failed create subject");
         }
 
    }

    @GetMapping
    public Iterable<Subject> findAllSubjects(){
        return subjectService.findAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> findSubject(@PathVariable("id") Long id){
        return ResponseEntity.ok(subjectService.findSubjectById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable("id") Long id){
        subjectService.deleteSubjectById(id);
    }




}
