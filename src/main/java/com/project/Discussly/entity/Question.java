package com.project.Discussly.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int upVotes;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "question_text",columnDefinition = "TEXT")
    private String questionText;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @CreationTimestamp
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Answer> answers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_upvote")
    private Set<User> upVotedUsers;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "question_tag")
    private Set<QuestionTag> questionTags;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    private int views;

}
