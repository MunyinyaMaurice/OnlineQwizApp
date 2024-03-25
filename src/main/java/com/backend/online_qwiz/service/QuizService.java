package com.backend.online_qwiz.service;

import com.backend.online_qwiz.dto.QuizDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.Role;
import com.backend.online_qwiz.model.User;
import com.backend.online_qwiz.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUser;

@Service
@RequiredArgsConstructor
public class QuizService {
    private  final QuizRepository quizRepository;

    public Quiz createQuiz(QuizDto quizDto){
        try {
            User user = getCurrentUser();
            if (user.getRole().equals(Role.ADMIN)){
                Quiz quiz = Quiz.builder()
                        .title(quizDto.getTitle())
                        .build();
                return quizRepository.save(quiz);
            }
            throw new RuntimeException("Your are not ADMIN");
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create Quiz. Please try again later.");
        }
        }

     public Quiz updateQuiz(Long quizId,QuizDto quizDto){
        Quiz quizExisting = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("quiz not found with this id:" + quizId));
         quizExisting.setTitle(quizDto.getTitle());
         return quizRepository.save(quizExisting);
     }
     public void deleteQuiz (Long quizId){
         Quiz quiz =  quizRepository.findById(quizId)
                 .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId));
         quizRepository.delete(quiz);
     }
     public List<Quiz> geetAllQuiz(){
      return quizRepository.findAll();
     }
     public Quiz getQuizById(Long quizId){
        Quiz quiz = quizRepository.findById(quizId)
         .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId));
        return quiz;
     }
    }

