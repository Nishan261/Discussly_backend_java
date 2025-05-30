package com.project.Discussly.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String tag;

    public QuestionTag(String tag) {
        this.tag = tag;
    }
}
