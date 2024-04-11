package com.backend.online_qwiz.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface QuizTakingServiceInterf {
   
    // ResponseEntity<String> startOrSubmitQuiz(Long quizId, Map<Long, String> answeredQuestions, boolean timerExpired,  Integer currentQuestionIndex);
    // public String getNextQuestionOptions(Long quizId);
   public ResponseEntity<?> getNextQuestionOptions(Long quizId);
}
