package com.backend.online_qwiz.service;

import com.backend.online_qwiz.dto.QuizQuestionDto;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.repository.QuizQuestionRepository;
import com.backend.online_qwiz.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;


    public QuizQuestion createQuizQuestion(QuizQuestionDto quizQuestionDto, Quiz quiz) {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestionText(quizQuestionDto.getQuestionText());
        quizQuestion.setOptions(quizQuestionDto.getOptions());
        quizQuestion.setCorrectOptionIndex(quizQuestionDto.getCorrectOptionIndex());
        quizQuestion.setQuiz(quiz);
        return quizQuestionRepository.save(quizQuestion);
    }

    public QuizQuestion updateQuizQuestion(Long questionId, QuizQuestionDto quizQuestionDto) {
        QuizQuestion existingQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        existingQuestion.setQuestionText(quizQuestionDto.getQuestionText());
        existingQuestion.setOptions(quizQuestionDto.getOptions());
        existingQuestion.setCorrectOptionIndex(quizQuestionDto.getCorrectOptionIndex());
        return quizQuestionRepository.save(existingQuestion);
    }

    public void deleteQuizQuestion(Long questionId) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));
        quizQuestionRepository.delete(quizQuestion);
    }

    public List<QuizQuestion> getAllQuizQuestions() {
        return quizQuestionRepository.findAll();
    }

    public List<QuizQuestion> getQuizQuestionsByQuizId(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + quizId));
//        Quiz quizz = quizzes.get(0); // Assuming there's only one quiz with a given title

        return quizQuestionRepository.findByQuizId(quizId);
    }
    
    public List<String> getQuestionOptions(Long questionId) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz question not found with ID: " + questionId));

        return quizQuestion.getOptions();
    }

}
