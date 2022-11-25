package com.quiz.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.quiz.app.models.Material;
import com.quiz.app.models.Question;
import com.quiz.app.models.Quiz;
import com.quiz.app.models.Subject;
import com.quiz.app.repositories.MaterialRepo;
import com.quiz.app.repositories.QuestionRepo;
import com.quiz.app.repositories.QuizRepo;
import com.quiz.app.repositories.SubjectRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class QuizService {
    private QuizRepo quizRepo;
    private SubjectRepo subjectRepo;
    private MaterialRepo materialRepo;
    private QuestionRepo questionRepo;

    public Quiz createQuiz(Quiz quiz) {
        Optional<Subject> subjectOptional = subjectRepo.findById(quiz.getSubject().getId());
       
        List<Question> questions = new ArrayList<>();
        quiz.setSubject(subjectOptional.get());
        List<Material> materials = materialRepo.findBySubject(quiz.getSubject());
        long ratio = quiz.getTotalQuestions()/materialRepo.countBySubject(quiz.getSubject());

        for(int i=0;i<ratio;i++){

            questionRepo.findRandomQuestionByMaterial(materials.get(i), ratio).stream().forEach((q)->{
                questions.add(q);
            });
        }

        Random random = new Random();

        for(int i=0;i<quiz.getTotalQuestions();i++){
            int index = random.nextInt(quiz.getTotalQuestions());
            quiz.getQuestions().add(questions.get(index));
            questions.remove(index);
            
        }
        
        quiz.setCreatedAt(new Date());
        quiz.setUpdatedAt(new Date());
        return quizRepo.save(quiz);
    }

    public Quiz updateQuiz(Quiz quiz) {
        quiz.setUpdatedAt(new Date());
        return quizRepo.save(quiz);
    }

    public Iterable<Quiz> findAllQuizzes(){
        return quizRepo.findAll();
    }

    public Quiz findQuizById(Long id){
        Optional<Quiz> quizOptional = quizRepo.findById(id);

        if(quizOptional.isPresent()){
            return quizOptional.get();
        }
        return null;
    }

    public void deleteQuizById(Long id){
        quizRepo.deleteById(id);
    }
}
