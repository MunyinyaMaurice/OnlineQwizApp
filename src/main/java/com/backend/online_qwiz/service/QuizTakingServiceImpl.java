package com.backend.online_qwiz.service;

//public class QuizTakingServiceImpl {
//}

import com.backend.online_qwiz.model.QuizQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizTakingServiceImpl implements QuizTakingServiceInterf {
    private final QuizQuestionService quizQuestionService;
    private final QuizResultService quizResultService;

    @Override

    public ResponseEntity<?> getNextQuestionOptions(Long quizId) {
        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
        if (quizQuestions.isEmpty()) {
            throw new IllegalArgumentException("No questions found for quiz with ID: " + quizId);
        }

        Map<String, Object> quizData = new HashMap<>();

        for (int currentQuestionIndex = 0; currentQuestionIndex < quizQuestions.size(); currentQuestionIndex++) {
            QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
            List<String> options = quizQuestionService.getQuestionOptions(currentQuestion.getId());

            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", currentQuestion.getQuestionText());
            questionData.put("options", options);

            quizData.put("Index " + (currentQuestionIndex + 1), questionData);
        }

        return ResponseEntity.ok(quizData);
    }

    public ResponseEntity<?> checkCompletionAndEndQuiz(Long quizId, Map<String, String> answeredQuestions) {
        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);
        if (answeredQuestions.size() == quizQuestions.size()) {
            // All questions answered, end the quiz
            return endQuiz(quizId, answeredQuestions);
        } else {
            // Not all questions answered yet
            return new ResponseEntity<>("Quiz cannot be ended. Please answer all questions.", HttpStatus.OK);
        }
    }
    
   
    public ResponseEntity<?> endQuiz(Long quizId,Map<String, String> answeredQuestions) {
   
  quizResultService.submitQuizResult(quizId, answeredQuestions);
  quizResultService.calculateScore(quizId, answeredQuestions);

return new ResponseEntity<>(HttpStatus.OK);

    }
}
