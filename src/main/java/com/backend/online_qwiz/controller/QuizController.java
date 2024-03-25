package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.dto.QuizDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.repository.QuizRepository;
import com.backend.online_qwiz.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class QuizController {
    private final QuizService quizService;
    @PostMapping
    public ResponseEntity<Quiz> craeteQuiz(@RequestBody @Valid QuizDto quizDto){
    try {
        Quiz quiz = quizService.createQuiz(quizDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
        }

    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long quizId, @RequestBody QuizDto quizDto) {
        Quiz updatedQuiz = quizService.updateQuiz(quizId, quizDto);
        return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all_quiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.geetAllQuiz();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
}
