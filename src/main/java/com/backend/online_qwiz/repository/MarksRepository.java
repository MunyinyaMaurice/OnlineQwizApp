package com.backend.online_qwiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.online_qwiz.model.Marks;
import com.backend.online_qwiz.model.Quiz;
import com.backend.online_qwiz.model.User;
@Repository
public interface MarksRepository extends JpaRepository<Marks, Long>{
    List<Marks> findByUserAndQuiz(User user, Quiz quiz);
    
}
