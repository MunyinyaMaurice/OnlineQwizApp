package com.backend.online_qwiz.service;

//public class QuizTakingServiceImpl {
//}

import com.backend.online_qwiz.model.QuizQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.quizTimer;

@Service
@RequiredArgsConstructor
public class QuizTakingServiceImpl implements QuizTakingServiceInterf {
    private final QuizQuestionService quizQuestionService;
    private final QuizResultService quizResultService;
    private final Timer timer;
    private final QuizService quizService;
//    private Timer quizTimer;
//    private final Timer timer;

    @Override
    public ResponseEntity<String> startOrSubmitQuiz(Long quizId, Map<Long, String> answeredQuestions, boolean timerExpired) {
        // If timer has expired, end the quiz
        if (timerExpired) {
            return endQuiz(quizId, answeredQuestions);
        }

        // If no questions answered yet, start the quiz
        if (answeredQuestions.isEmpty()) {
            startQuizTimer(quizId);
            return new ResponseEntity<>("Quiz started! You have 10 minutes to complete it.", HttpStatus.OK);
        }

        // Check if all questions have been answered
        return checkCompletionAndEndQuiz(quizId, answeredQuestions);
    }

    private ResponseEntity<String> checkCompletionAndEndQuiz(Long quizId, Map<Long, String> answeredQuestions) {
        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
        if (answeredQuestions.size() == quizQuestions.size()) {
            // All questions answered, end the quiz
            return endQuiz(quizId, answeredQuestions);
        } else {
            // Not all questions answered yet
            return new ResponseEntity<>("Quiz cannot be ended. Please answer all questions.", HttpStatus.OK);
        }
    }
//    public String endQuiz(Long quizId, Map<Long, String> answeredQuestions) {
//        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
//
//        // Check if all questions have been answered
//        if (answeredQuestions.size() == quizQuestions.size()) {
//            // Stop the timer if it's running
//            timer.cancel(); // Assuming timer is an instance of java.util.Timer
//
//            // Calculate the score
//            int score = quizResultService.calculateScore(answeredQuestions);
//
//            // Store the quiz result
//            quizResultService.storeQuizResult(quizId, answeredQuestions);
//
//            return "Quiz ended. Your score is: " + score;
//        } else {
//            return "Quiz cannot be ended. Please answer all questions.";
//        }
//    }

    private ResponseEntity<String> endQuiz(Long quizId, Map<Long, String> answeredQuestions) {
        // Stop the timer
        timer.cancel(); // Assuming timer is an instance of java.util.Timer

        // Calculate the score
        int score = quizResultService.calculateScore(answeredQuestions);

        // Store the quiz result
        quizResultService.submitQuizResult(quizId, answeredQuestions);

        return new ResponseEntity<>("Quiz ended. Your score is: " + score, HttpStatus.OK);
    }

    private void startQuizTimer(Long quizId) {
        long duration = 10 * 60 * 1000; // 10 minutes in milliseconds

//        quizTimer = new Timer();
        quizTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                endQuiz(quizId, null); // Assuming null for answeredQuestions when timer expires
            }
        }, duration);
    }
}
