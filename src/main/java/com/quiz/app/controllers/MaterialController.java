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
import com.quiz.app.models.Material;
import com.quiz.app.services.MaterialService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/materials")
@AllArgsConstructor
public class MaterialController {

    private MaterialService materialService;

    @PostMapping
    public ResponseEntity<?> createMaterial(@Valid @RequestBody Material material, Errors errors){

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
            return ResponseEntity.ok(materialService.createMaterial(material)) ;
        } catch (Exception e) {
            throw new RuntimeException("Failed create material");
        } 
    }

    @PutMapping
    public ResponseEntity<?> updateMaterial(@Valid @RequestBody Material material, Errors errors){
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
            return ResponseEntity.ok(materialService.updateMaterial(material));
        } catch (Exception e) {
            throw new RuntimeException("Failed update material");
        }
    }

    @GetMapping
    public Iterable<Material> findAllMaterials(){
        return materialService.findAllMaterials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> findMaterial(@PathVariable("id") Long id){
        return ResponseEntity.ok(materialService.findMaterialById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable("id") Long id){
        materialService.deleteMaterialById(id);
    }
    
}
