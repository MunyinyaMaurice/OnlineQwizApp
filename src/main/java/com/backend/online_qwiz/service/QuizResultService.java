package com.backend.online_qwiz.service;

import com.backend.online_qwiz.model.Marks;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.model.QuizResult;
import com.backend.online_qwiz.model.User;
import com.backend.online_qwiz.repository.MarksRepository;
import com.backend.online_qwiz.repository.QuizQuestionRepository;
import com.backend.online_qwiz.repository.QuizRepository;
import com.backend.online_qwiz.repository.QuizResultRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUser;
//import static com.backend.online_qwiz.security.config.ApplicationConfig.getCurrentUser;

@Service
@RequiredArgsConstructor
public class QuizResultService {
    private final QuizResultRepository quizResultRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final MarksRepository marksRepository;

//    public QuizResult submitQuizResult(Long questionId, String selectedOption) {
//        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
//                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
//
//        QuizResult quizResult = new QuizResult();
//        quizResult.setQuizQuestion(quizQuestion);
//        quizResult.setUser(getCurrentUser());
//        quizResult.setSelectedOption(selectedOption);
//        return quizResultRepository.save(quizResult);
//    }
// public ResponseEntity<?> submitQuizResult(Long quizId, Map<Long, String> answeredQuestions) {
//     for (Map.Entry<Long, String> entry : answeredQuestions.entrySet()) {
//         Long questionId = entry.getKey();
//         String selectedOption = entry.getValue();
//         // Submit each quiz result individually
//         submitQuizResult(quizId, questionId, selectedOption);
//         return ResponseEntity.ok("Quiz result submitted successfully.");
//     }
// }
public ResponseEntity<List<QuizResult>> submitQuizResult(Long quizId, Map<String, String> answeredQuestions) {
    List<QuizResult> submittedResults = new ArrayList<>();
    for (Map.Entry<String, String> entry : answeredQuestions.entrySet()) {
        Long questionId =  Long.parseLong(entry.getKey());
        String selectedOption = entry.getValue();
        QuizResult submittedResult = submitQuizResult(quizId, questionId, selectedOption);
        submittedResults.add(submittedResult);
        // calculateScore(quizId, submittedResult);
    }
    return ResponseEntity.ok(submittedResults);
}

    private QuizResult submitQuizResult(Long quizId, Long questionId, String selectedOption) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        // Retrieve the quiz entity by quizId
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId));
        // Create and save the quiz result
        QuizResult quizResult = new QuizResult();
        quizResult.setQuiz(quiz);
        quizResult.setQuizQuestion(quizQuestion);
        quizResult.setUser(getCurrentUser());
        quizResult.setSelectedOption(selectedOption);
        // Save the quiz result
        return quizResultRepository.save(quizResult);
    }

    public ResponseEntity<?> getLastQuizResultByUserAndQuiz(Long quizId) {
        // Map<Long, String> response = new HashMap<>();
        // getQuizResultsByUserAndQuiz
        User currentUser = getCurrentUser();
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId));
    
                // ResponseEntity<?> quizMarks = marksRepository.findByUserAndQuiz(currentUser, quiz);
        List<Marks> quizMarks = marksRepository.findByUserAndQuiz(currentUser, quiz);
    
        if (quizMarks.isEmpty()) {
    
          return null;
    
        }
    
        // return quizMarks.get(quizMarks.size() - 1);
        return ResponseEntity.ok(quizMarks.get(quizMarks.size() - 1));
    
      }

    public List<QuizResult> getQuizResultsByUser() {
        return quizResultRepository.findByUser(getCurrentUser());
    }

    public List<QuizResult> getQuizResultsByQuestion(Long questionId) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        return quizResultRepository.findByQuizQuestion(quizQuestion);
    }

    public List<QuizResult> getAllQuizResults() {
        return quizResultRepository.findAll();
    }
public int calculateScore(Long quizId, Map<String, String> answeredQuestions) {
    int totalScore = 0;
    for (Map.Entry<String, String> entry : answeredQuestions.entrySet()) {
        Long questionId =  Long.parseLong(entry.getKey());
        String selectedOption = entry.getValue();
        // Retrieve the QuizQuestion associated with the questionId
        QuizQuestion question = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        // Check if the selected option matches the correct option
        if (selectedOption.equals(question.getOptions().get(question.getCorrectOptionIndex()))) {
            // Increment the total score if the selected option matches the correct option
            totalScore++;
        }
    }
    Marks marks = Marks.builder()
    .quiz(quizRepository.findById(quizId).get())
    .user(getCurrentUser())
    .marks(totalScore)
    .build();
    marksRepository.save(marks);
    return totalScore;
}

}
