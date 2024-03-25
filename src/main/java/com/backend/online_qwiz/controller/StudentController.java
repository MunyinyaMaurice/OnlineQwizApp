package com.backend.online_qwiz.controller;

import com.backend.online_qwiz.dto.QuizResultDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.model.QuizResult;
import com.backend.online_qwiz.service.QuizQuestionService;
import com.backend.online_qwiz.service.QuizResultService;
import com.backend.online_qwiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
//
//@RestController
//@RequestMapping("/api/student")
//@RequiredArgsConstructor
//public class StudentController {
//    private final QuizQuestionService quizQuestionService;
//    private final QuizResultService quizResultService;
//
//    @PostMapping("/quiz/{quizId}/start")
//    public ResponseEntity<String> startQuiz(@PathVariable Long quizId) {
//        // Get quiz questions for the given quizId
//        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
//
//        // Start timer for 10 minutes
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // End quiz and calculate score when timer expires
//                endQuiz(quizId);
//                timer.cancel(); // Cancel timer after quiz ends
//            }
//        }, 10 * 60 * 1000); // 10 minutes
//
//        // Return success message with quiz questions
//        return new ResponseEntity<>("Quiz started! You have 10 minutes to complete it.", HttpStatus.OK);
//    }
//
//    @PostMapping("/quiz/{quizId}/submit")
//    public ResponseEntity<String> submitQuiz(@PathVariable Long quizId, @RequestBody String selectedOption) {
//        // Record quiz results
//        quizResultService.submitQuizResult(quizId,selectedOption);
//
//        // End quiz and calculate score
//        endQuiz(quizId);
//
//        return new ResponseEntity<>("Quiz submitted successfully!", HttpStatus.OK);
//    }
//
//    private void endQuiz(Long quizId) {
//        // Calculate score and end quiz
//        // This method will be called either when the timer expires or when the quiz is manually submitted
//        // Calculate score based on quiz results
//        // Record score in database
//        // You can implement this logic based on your requirements
//    }
//}
@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class StudentController {
    private final QuizQuestionService quizQuestionService;
    private final QuizResultService quizResultService;
    private final QuizService quizService;

//    @PostMapping("/quiz/start/{quizId}")
//    public ResponseEntity<String> startOrSubmitQuiz(@PathVariable Long quizId, @RequestBody(required = false) String selectedOption) {
//        if (selectedOption == null || selectedOption.isEmpty()) {
//            // Start the quiz
//            // Get quiz questions for the given quizId
//            List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
//
//            Quiz quiz = quizService.getQuizById(quizId);
//
//            // Start timer for 10 minutes
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    try {
//                        // End quiz and calculate score when timer expires
//                        String result = endQuiz(quiz);
//                        // You may want to return the result here or handle it as needed
//                    } catch (Exception e) {
//                        e.printStackTrace(); // Handle or log the exception
//                    } finally {
//                        timer.cancel(); // Cancel timer after quiz ends or if an exception occurs
//                    }
//                }
//            }, 10 * 60 * 1000); // 10 minutes
//
//            // Return success message with quiz questions
//            return new ResponseEntity<>("Quiz started! You have 10 minutes to complete it.", HttpStatus.OK);
//        } else {
//            // Submit the quiz
//            try {
//                // Record quiz results
//                quizResultService.submitQuizResult(quizId, selectedOption);
//                // You may choose to return a response immediately or wait for the timer to end
//                return new ResponseEntity<>("Quiz submitted successfully!", HttpStatus.OK);
//            } catch (Exception e) {
//                e.printStackTrace(); // Handle or log the exception
//                return new ResponseEntity<>("Failed to submit quiz. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//    }


    // Method to end the quiz, calculate the score, and return the result
    private String endQuiz(Quiz quiz) {
        // Implement your logic here to end the quiz, calculate the score, and return the result
        // For example:
        // Calculate the score based on the submitted quiz results
        // String result = quizResultService.calculateScore(quiz);
        // return result;
        return "Quiz ended and score calculated.";
    }


//    private String endQuiz(Quiz quizId) {
//        // Get quiz results for the current user and quiz
//        List<QuizResult> quizResults = quizResultService.getQuizResultsByUserAndQuiz(quizId);
//
//        // Calculate score
//        int score = calculateScore(quizResults);
//
//        // You can format the result message as needed
//        return "Quiz submitted successfully! Your score is: " + score;
//    }

    private int calculateScore(List<QuizResult> quizResults) {
        // Your score calculation logic here
        // This is just a placeholder, replace it with your actual logic
        return quizResults.size(); // For demonstration, returning the number of questions answered
    }
}
