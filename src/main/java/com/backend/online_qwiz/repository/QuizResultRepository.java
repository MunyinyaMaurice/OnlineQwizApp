package com.backend.online_qwiz.repository;

import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import com.backend.online_qwiz.model.QuizResult;
import com.backend.online_qwiz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
    List<QuizResult>getQuizByUserId(Integer userId);

//    @Override
    List<QuizResult> findByUser(User user);
    List<QuizResult> findByUserAndQuiz(User user, Quiz quiz);
//    List<QuizResult>findByUserAndQuiz(Long quizId,User user);
    List<QuizResult> findByQuizQuestion(QuizQuestion quizQuestion);
}
