package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.service.QuizTakingServiceInterf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class QuizTakingController {
    private final QuizTakingServiceInterf quizTakingService;


    @PostMapping("/start/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable Long quizId) {
        // Initialize answeredQuestions with an empty map
        Map<Long, String> answeredQuestions = new HashMap<>();
        return quizTakingService.startOrSubmitQuiz(quizId, answeredQuestions, false);
    }

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<String> submitQuiz(@PathVariable Long quizId, @RequestBody Map<Long, String> answeredQuestions) {
        return quizTakingService.startOrSubmitQuiz(quizId, answeredQuestions, false);
    }
    @GetMapping("/end/{quizId}")
    public ResponseEntity<String> endQuiz(@PathVariable Long quizId, @RequestBody Map<Long, String> answeredQuestions) {
        return quizTakingService.startOrSubmitQuiz(quizId, answeredQuestions, true);
    }
}

