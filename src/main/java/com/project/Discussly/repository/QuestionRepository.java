package com.project.Discussly.repository;

import com.project.Discussly.entity.Question;
import com.project.Discussly.entity.QuestionTag;
import com.project.Discussly.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByOrderByTimestampDesc(Pageable pageable);
    List<Question> findByQuestionTagsIn(List<QuestionTag> tags,Pageable pageable);
    @Query(value = "SELECT * FROM question WHERE to_tsvector('english', question_title || ' ' || question_text) @@ to_tsquery('english', ?1)" ,
                     countQuery = "SELECT count(*) FROM question WHERE to_tsvector('english', question_title || ' ' || question_text) @@ to_tsquery('english', ?1)",nativeQuery = true)
    List<Question> search(String query,Pageable pageable);

    List<Question> findAllByUserOrderByTimestampDesc(User u);

}
