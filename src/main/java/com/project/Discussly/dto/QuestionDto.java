package com.project.Discussly.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionDto {
    private int id;
    private int voteCount;
    private LocalDateTime timestamp;
    private String questionText;
    private String questionTitle;
    private int userId;
    private List<AnswerDto> answers;
    private boolean isUpVoted;
    private List<String> tags;
    private int answerCount;

    private int originalQuestionId;
    private int dupMarkingUserId;

    private int views;
}
