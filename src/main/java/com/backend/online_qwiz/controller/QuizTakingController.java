package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.model.Marks;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.repository.MarksRepository;
import com.backend.online_qwiz.repository.QuizQuestionRepository;
import com.backend.online_qwiz.repository.QuizRepository;
import com.backend.online_qwiz.service.QuizQuestionService;
import com.backend.online_qwiz.service.QuizResultService;
import com.backend.online_qwiz.service.QuizService;
import com.backend.online_qwiz.service.QuizTakingServiceImpl;
import com.backend.online_qwiz.service.QuizTakingServiceInterf;
import com.fasterxml.jackson.core.JsonProcessingException;
import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUser;

import jakarta.persistence.EntityNotFoundException;
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
    private final  QuizService quizService ;
    private final QuizResultService quizResultService;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final MarksRepository marksRepository;
    @GetMapping("/options/{questionId}")
    public ResponseEntity<List<String>> getQuestionOptions(@PathVariable Long questionId) {
        List<String> options = quizQuestionService.getQuestionOptions(questionId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }
   
    
    @GetMapping("/questions/{quizId}")
    public ResponseEntity<?> getAllQuizQuestions(@PathVariable Long quizId) {
        // Map<String,Object> response = new HashMap<>();
        try {
            ResponseEntity<?> quizData = quizTakingServiceImpl.getNextQuestionOptions(quizId);
            return new ResponseEntity<>(quizData,HttpStatus.OK);
        
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/submit/{quizId}")
    public ResponseEntity<?> submit(
            @PathVariable Long quizId,
            @RequestBody Map<String, String> submissionData) {
            // return quizResultService.submitQuizResult(quizId, submissionData);
            return quizTakingServiceImpl.endQuiz(quizId, submissionData);
            }

    @PostMapping("/end/{quizId}")
    public ResponseEntity<?> endQuiz(
            @PathVariable Long quizId,
            @RequestBody Map<String, String> submissionData) {

        try {
            // Process each submitted answer
            int totalScore = 0;

            for (Map.Entry<String, String> entry : submissionData.entrySet()) {
                Long questionId = Long.parseLong(entry.getKey());
                String selectedOption = entry.getValue();

                // Retrieve the QuizQuestion associated with the questionId
                QuizQuestion question = quizQuestionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));

                // Check if the selected option matches the correct option
                if (selectedOption.equals(question.getOptions().get(question.getCorrectOptionIndex()))) {
                    // Increment the total score if the selected option is correct
                    totalScore++;
                }
            }

            // Save the quiz result with the calculated score
            Marks marks = Marks.builder()
                    .quiz(quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId)))
                    .user(getCurrentUser())
                    .marks(totalScore)
                    .build();
            marksRepository.save(marks);

          // Return the total score as a JSON response
          return ResponseEntity.ok(Map.of("totalScore", totalScore));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process quiz answers. Error: " + e.getMessage());
        }
    }

    // @PostMapping("/result/{quizId}")
    // public ResponseEntity<?> quizResult(
    //         @PathVariable Long quizId,
    //         @RequestBody Map<Long, String> answeredQuestions) {

    //     // Calculate the score
    //     int score = quizResultService.calculateScore(answeredQuestions);

    //     // Store the quiz result
    //     quizResultService.submitQuizResult(quizId, answeredQuestions);

    //     // Construct the JSON response
    //     Map<String, Object> responseBody = Map.of(
    //         "quizId", quizId,
    //         "score", score
    //                 );

    //     return ResponseEntity.ok(responseBody);
    // }
}

