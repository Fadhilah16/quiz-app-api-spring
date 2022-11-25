package com.quiz.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.quiz.app.models.Answer;
import com.quiz.app.models.Attempt;
import com.quiz.app.models.AttemptAnswer;
import com.quiz.app.models.Question;
import com.quiz.app.models.Quiz;
import com.quiz.app.models.User;
import com.quiz.app.repositories.AnswerRepo;
import com.quiz.app.repositories.AttemptAnswerRepo;
import com.quiz.app.repositories.AttemptRepo;
import com.quiz.app.repositories.QuestionRepo;
import com.quiz.app.repositories.QuizRepo;
import com.quiz.app.services.user.UserService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AttemptService {
    
    private AttemptRepo attemptRepo;
    private AttemptAnswerRepo attemptAnswerRepo;
    private AnswerRepo answerRepo;
    private QuizRepo quizRepo;
    private QuestionRepo questionRepo;
    private UserService userService;

    public Attempt createAttempt(Attempt attempt, HttpServletRequest request){
        User user = userService.findUserByJwtUsername(request).orElseThrow(() -> new RuntimeException("user doesn't exist"));
        Quiz quiz = quizRepo.findById(attempt.getQuiz().getId()).orElseThrow(() -> new RuntimeException("quiz doesn't exist"));
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setScore(0.);
        attempt.setPassStatus(false);
        attempt.setCreatedAt(new Date());
        attempt.setUpdatedAt(new Date());
        return attemptRepo.save(attempt);
    }

    public List<AttemptAnswer> createAttemptAnswer(List<AttemptAnswer> attemptAnswers){
        List<AttemptAnswer> savedAnswers = new ArrayList<>();
        for(AttemptAnswer attemptAnswer: attemptAnswers){
            Attempt attempt = attemptRepo.findById(attemptAnswer.getAttempt().getId()).orElseThrow(() -> new RuntimeException("attempt doesn't exist"));
            Answer answer = answerRepo.findById(attemptAnswer.getAnswer().getId()).orElseThrow(() -> new RuntimeException("answer doesn't exist"));
            Question question = questionRepo.findById(attemptAnswer.getAnswer().getId()).orElseThrow(() -> new RuntimeException("answer doesn't exist"));
            attemptAnswer.setAttempt(attempt);
            attemptAnswer.setAnswer(answer);
            attemptAnswer.setQuestion(question);
            savedAnswers.add(attemptAnswerRepo.save(attemptAnswer));
        }

        return savedAnswers;
    }

    public Attempt updateAttempt(Attempt attempt){
        List<AttemptAnswer> attemptAnswers = attemptAnswerRepo.findByAttempt(attempt);
        Integer correctAmount = 0;
        Double score;
        for (AttemptAnswer attemptAnswer : attemptAnswers) {
            if(attemptAnswer.getAnswer().getIsCorrect()){
                correctAmount ++;
            }
        }

        score = (correctAmount/attempt.getQuiz().getTotalQuestions())*100.;
        attempt.setScore(score);
        if(score > 75){
            attempt.setPassStatus(true);
        }else{
            attempt.setPassStatus(false);
        }

        return attemptRepo.save(attempt);
    }

    public List<Attempt> findAllAttempts(){
        return attemptRepo.findAll();
    }

    public List<Attempt> findAllAttemptAnswersByUser(String username){
        Optional<User> userOptional = userService.findUserByUsername(username);

        if (userOptional.isPresent()){
            return attemptRepo.findByUser(userOptional.get());
        }

        return null;
    }

    public List<Attempt> findAllAttemptAnswersByUser(HttpServletRequest request){
        Optional<User> userOptional = userService.findUserByJwtUsername(request);

        if (userOptional.isPresent()){
            return attemptRepo.findByUser(userOptional.get());
        }

        return null;
    }

    public List<AttemptAnswer> findAllAttemptAnswers(Attempt attempt){
        return attemptAnswerRepo.findByAttempt(attempt);

    }

    public Attempt findAttemptById(Long id){
        Optional<Attempt> attemptOptional = attemptRepo.findById(id);

        if(attemptOptional.isPresent()){
            return attemptOptional.get();
        }
        return null;
    }

    public void deleteAttemptById(Long id){
        attemptRepo.deleteById(id);
    }

    public void deleteAttemptAnswerById(Long id){
        attemptAnswerRepo.deleteById(id);
    }

}
