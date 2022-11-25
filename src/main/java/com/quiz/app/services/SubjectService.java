package com.quiz.app.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.quiz.app.models.Subject;
import com.quiz.app.repositories.SubjectRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SubjectService {
    private SubjectRepo subjectRepo;

    public Subject createSubject(Subject subject){
        subject.setCreatedAt(new Date());
        subject.setUpdatedAt(new Date());
        return subjectRepo.save(subject);
    }

    public Subject updateSubject(Subject subject){
        subject.setUpdatedAt(new Date());
        return subjectRepo.save(subject);
    }

    public Iterable<Subject> findAllSubjects(){
        return subjectRepo.findAll();
    }

    public Subject findSubjectById(Long id){
        Optional<Subject> subjectOptional = subjectRepo.findById(id);

        if (subjectOptional.isPresent()){
            return subjectOptional.get();
        }

        return null;
        
    }
    
    public void deleteSubjectById(Long id){
        subjectRepo.deleteById(id);
    }
}
