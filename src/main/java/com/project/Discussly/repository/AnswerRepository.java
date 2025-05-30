package com.project.Discussly.repository;

import com.project.Discussly.entity.Answer;
import com.project.Discussly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    long countAllByUser(User user);
}
