package com.backend.online_qwiz.service;

import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.model.QuizResult;
import com.backend.online_qwiz.model.User;
import com.backend.online_qwiz.repository.QuizQuestionRepository;
import com.backend.online_qwiz.repository.QuizResultRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.backend.online_qwiz.secuirity.config.ApplicationConfig.getCurrentUser;
//import static com.backend.online_qwiz.security.config.ApplicationConfig.getCurrentUser;

@Service
@RequiredArgsConstructor
public class QuizResultService {
    private final QuizResultRepository quizResultRepository;
    private final QuizQuestionRepository quizQuestionRepository;

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
public void submitQuizResult(Long quizId, Map<Long, String> answeredQuestions) {
    for (Map.Entry<Long, String> entry : answeredQuestions.entrySet()) {
        Long questionId = entry.getKey();
        String selectedOption = entry.getValue();
        // Submit each quiz result individually
        submitQuizResult(questionId, selectedOption);
    }
}

    private QuizResult submitQuizResult(Long questionId, String selectedOption) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        QuizResult quizResult = new QuizResult();
        quizResult.setQuizQuestion(quizQuestion);
        quizResult.setUser(getCurrentUser());
        quizResult.setSelectedOption(selectedOption);
        return quizResultRepository.save(quizResult);
    }
    public List<QuizResult> getQuizResultsByUserAndQuiz(Quiz quizId) {
        User currentUser = getCurrentUser();
        return quizResultRepository.findByUserAndQuiz(currentUser, quizId);
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
//    public int calculateScore(Map<Long, String> answeredQuestions) {
//        int totalScore = 0;
//        for (Map.Entry<Long, String> entry : answeredQuestions.entrySet()) {
//            Long questionId = entry.getKey();
//            String selectedOption = entry.getValue();
//            // Retrieve the QuizQuestion associated with the questionId
//            QuizQuestion question = quizQuestionRepository.findById(questionId)
//                    .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
//            // Check if the selected option matches the correct option index
//            if (selectedOption.equals(question.getOptions().get(question.getCorrectOptionIndex()))) {
//                // Increment the total score if the selected option matches the correct option
//                totalScore += totalScore +1;
//            }
//
//        }
//        return totalScore;
//    }
public int calculateScore(Map<Long, String> answeredQuestions) {
    int totalScore = 0;
    for (Map.Entry<Long, String> entry : answeredQuestions.entrySet()) {
        Long questionId = entry.getKey();
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
    return totalScore;
}

}
