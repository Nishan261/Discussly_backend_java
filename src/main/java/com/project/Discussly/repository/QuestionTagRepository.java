package com.project.Discussly.repository;

import com.project.Discussly.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionTagRepository extends JpaRepository<QuestionTag,Integer> {

    Optional<QuestionTag> findByTag(String tag);

    @Query(value = "SELECT * FROM tags WHERE to_tsvector('english', tag) @@ to_tsquery('english', ?1)", nativeQuery = true)
    Set<QuestionTag> findAllByTags(String tags);

    @Query(value = "SELECT tag FROM tags order by random() limit 10", nativeQuery = true)
    List<String> getsomeTags();

}
