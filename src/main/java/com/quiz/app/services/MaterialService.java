package com.quiz.app.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.quiz.app.repositories.MaterialRepo;
import com.quiz.app.repositories.SubjectRepo;
import com.quiz.app.models.Material;
import com.quiz.app.models.Subject;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class MaterialService {
    private MaterialRepo materialRepo;
    private SubjectRepo subjectRepo;

    public Material createMaterial(Material material) {
        Optional<Subject> subjectOptional = subjectRepo.findById(material.getSubject().getId());
        material.setSubject(subjectOptional.get());
        material.setCreatedAt(new Date());
        material.setUpdatedAt(new Date());
        return materialRepo.save(material);
    }

    public Material updateMaterial(Material material) {
        material.setUpdatedAt(new Date());
        return materialRepo.save(material);
    }

    public Iterable<Material> findAllMaterials(){
        return materialRepo.findAll();
    }

    public Material findMaterialById(Long id){
        Optional<Material> materialOptional = materialRepo.findById(id);

        if(materialOptional.isPresent()){
            return materialOptional.get();
        }

        return null;
    }

    public void deleteMaterialById(Long id){
        materialRepo.deleteById(id);
    }
}
