package com.backend.online_qwiz.repository;

import com.backend.online_qwiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
//List<Quiz> findByTitle(String title);
}
