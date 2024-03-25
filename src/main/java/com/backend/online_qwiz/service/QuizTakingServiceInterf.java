package com.backend.online_qwiz.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface QuizTakingServiceInterf {
    /**
     * Starts or submits a quiz for a student.
     * @param quizId The ID of the quiz.
     * @param answeredQuestions A map containing the IDs of the answered questions and their corresponding selected options.
     * @param timerExpired Indicates whether the quiz timer has expired.
     * @return A ResponseEntity with a message indicating the result of the operation.
     */
    ResponseEntity<String> startOrSubmitQuiz(Long quizId, Map<Long, String> answeredQuestions, boolean timerExpired);
}
