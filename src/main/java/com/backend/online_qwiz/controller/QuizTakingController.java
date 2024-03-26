package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.service.QuizQuestionService;
import com.backend.online_qwiz.service.QuizTakingServiceImpl;
import com.backend.online_qwiz.service.QuizTakingServiceInterf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class QuizTakingController {
    private final QuizTakingServiceImpl quizTakingServiceImpl;
    private final QuizQuestionService quizQuestionService;


    @PostMapping("/start/{quizId}")
    public ResponseEntity<String> startQuiz(@PathVariable Long quizId) {
        // Initialize answeredQuestions with an empty map
        Map<Long, String> answeredQuestions = new HashMap<>();
        return quizTakingServiceImpl.startOrSubmitQuiz(quizId, answeredQuestions, false);
    }

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<String> submitQuiz(@PathVariable Long quizId, @RequestBody Map<Long, String> answeredQuestions) {
        return quizTakingServiceImpl.startOrSubmitQuiz(quizId, answeredQuestions, false);
    }
    @GetMapping("/end/{quizId}")
    public ResponseEntity<String> endQuiz(@PathVariable Long quizId, @RequestBody Map<Long, String> answeredQuestions) {
        return quizTakingServiceImpl.startOrSubmitQuiz(quizId, answeredQuestions, true);
    }

    @GetMapping("/options/{questionId}")
    public ResponseEntity<List<String>> getQuestionOptions(@PathVariable Long questionId) {
        List<String> options = quizQuestionService.getQuestionOptions(questionId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

}

