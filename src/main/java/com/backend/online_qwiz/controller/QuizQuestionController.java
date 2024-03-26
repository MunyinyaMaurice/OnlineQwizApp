package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.dto.QuizQuestionDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.service.QuizQuestionService;
import com.backend.online_qwiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class QuizQuestionController {
        private final QuizQuestionService quizQuestionService;
        private final QuizService quizService;

        @PostMapping("/{quizId}")
        public ResponseEntity<QuizQuestion> createQuizQuestion(@PathVariable Long quizId, @RequestBody QuizQuestionDto quizQuestionDto) {
            // Assuming you have a service method to fetch the quiz by ID
            Quiz quiz = quizService.getQuizById(quizId);
            QuizQuestion createdQuestion = quizQuestionService.createQuizQuestion(quizQuestionDto, quiz);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        }

        @PutMapping("/{questionId}")
        public ResponseEntity<QuizQuestion> updateQuizQuestion(@PathVariable Long questionId, @RequestBody QuizQuestionDto quizQuestionDto) {
            QuizQuestion updatedQuestion = quizQuestionService.updateQuizQuestion(questionId, quizQuestionDto);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        }

        @DeleteMapping("/{questionId}")
        public ResponseEntity<Void> deleteQuizQuestion(@PathVariable Long questionId) {
            quizQuestionService.deleteQuizQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("/quiz/all_questions")
        public ResponseEntity<List<QuizQuestion>> getAllQuizQuestions() {
            List<QuizQuestion> quizQuestions = quizQuestionService.getAllQuizQuestions();
            return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
        }

       @GetMapping("/quiz/{quizId}")
       public ResponseEntity<List<QuizQuestion>> getQuizQuestionsByQuiz(@PathVariable Long quizId) {
           // Assuming you have a service method to fetch quiz questions by quiz ID
           List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
           return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
       }
}
