package com.backend.online_qwiz.service;

import com.backend.online_qwiz.dto.QuizDto;
import com.backend.online_qwiz.dto.QuizQuestionDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.model.Role;
import com.backend.online_qwiz.model.User;
import com.backend.online_qwiz.repository.QuizQuestionRepository;
import com.backend.online_qwiz.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUser;

@Service
@RequiredArgsConstructor
public class QuizService {
    private  final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;

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
        @Transactional
    public Quiz updateQuiz(Long quizId, QuizDto quizDto) {
        Quiz existingQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));

        // Update the title of the quiz
        existingQuiz.setTitle(quizDto.getTitle());

        // Process each QuizQuestionDto in QuizDto
        for (QuizQuestionDto questionDto : quizDto.getQuestions()) {
            QuizQuestion question;
            if (questionDto.getId() != null) {
                // Existing QuizQuestion: Fetch and update
                question = quizQuestionRepository.findById(questionDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("QuizQuestion not found with id: " + questionDto.getId()));
            } else {
                // New QuizQuestion: Create a new instance
                question = new QuizQuestion();
                question.setQuiz(existingQuiz); // Set the relationship
            }

            // Update or set QuizQuestion details
            question.setQuestionText(questionDto.getQuestionText());
            question.setOptions(questionDto.getOptions());
            question.setCorrectOptionIndex(questionDto.getCorrectOptionIndex());

            // Save the QuizQuestion (either update or create)
            quizQuestionRepository.save(question);
        }

        // Save and return the updated quiz
        return quizRepository.save(existingQuiz);
    }
    @Transactional
    public void deleteQuiz(Long quizId) {
        // Find the Quiz entity by ID
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + quizId));

        // Delete the associated QuizQuestion entities (if any)
        List<QuizQuestion> questions = quiz.getQuestions();
        if (questions != null && !questions.isEmpty()) {
            for (QuizQuestion question : questions) {
                quizQuestionRepository.delete(question);
            }
        }

        // Delete the Quiz entity
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

