package com.backend.online_qwiz.repository;

import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {
//    List<QuizQuestion> findByQuiz (Long quizId);
    List<QuizQuestion> findByQuizId(Long quizId);
}
