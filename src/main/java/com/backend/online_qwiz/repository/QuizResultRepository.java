package com.backend.online_qwiz.repository;

import com.backend.online_qwiz.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
}
