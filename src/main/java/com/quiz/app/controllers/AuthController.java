package com.quiz.app.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.app.dtos.ResponseDto;
import com.quiz.app.dtos.auth.LoginRequestDto;
import com.quiz.app.models.User;
import com.quiz.app.services.user.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user, Errors errors) {
        try{         
                ResponseDto response = new ResponseDto();
                if(errors.hasErrors()){
                    for (ObjectError error : errors.getAllErrors()) {
                        System.out.println(error.getDefaultMessage());
                        response.getMessages().add(error.getDefaultMessage());
                    }
                    response.setStatus("400");
                    
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            return ResponseEntity.ok(userService.createUser(user)) ;
        }catch(Exception e){
            // throw new RuntimeException("Failed create account");
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequestDto loginRequest, Errors errors) {
		ResponseDto response = new ResponseDto();

        try{
            if(errors.hasErrors()){
                for (ObjectError error : errors.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                    response.getMessages().add(error.getDefaultMessage());
                }
                response.setStatus("400");
                
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.ok(userService.userIsAuthenticated(loginRequest));
        } catch (Exception e){
            throw new RuntimeException("Failed while login");
        }

		
	}

}
