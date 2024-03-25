package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.dto.QuizResultDto;
import com.backend.online_qwiz.model.QuizResult;
import com.backend.online_qwiz.service.QuizResultService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUserId;

@RestController
@RequestMapping("/api/v2/auth/quiz-results")
@RequiredArgsConstructor
public class QuizResultController {
    private final QuizResultService quizResultService;

//    @PostMapping("/{questionId}")
//    public ResponseEntity<QuizResult> submitQuizResult(
//            @PathVariable Long questionId,
//            @RequestBody QuizResultDto quizResultDto) {
////        Integer userId = getCurrentUserId();
////        Long userIdLong = userId.longValue();
//        QuizResult submittedResult = quizResultService.submitQuizResult( questionId, quizResultDto.getSelectedOption());
//        return new ResponseEntity<>(submittedResult, HttpStatus.CREATED);
//    }

    @GetMapping("/user_result")
    public ResponseEntity<List<QuizResult>> getQuizResultsByUser() {
        List<QuizResult> quizResults = quizResultService.getQuizResultsByUser();
        return new ResponseEntity<>(quizResults, HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<QuizResult>> getQuizResultsByQuestion(@PathVariable Long questionId) {
        List<QuizResult> quizResults = quizResultService.getQuizResultsByQuestion(questionId);
        return new ResponseEntity<>(quizResults, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<QuizResult>> getAllQuizResults() {
        List<QuizResult> quizResults = quizResultService.getAllQuizResults();
        return new ResponseEntity<>(quizResults, HttpStatus.OK);
    }
}