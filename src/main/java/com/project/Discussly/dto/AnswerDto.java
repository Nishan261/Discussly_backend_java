package com.project.Discussly.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDto {
    private int id;
    private String answer;
    private int userId;
    private int voteCount;
    private LocalDateTime timestamp;
    private boolean isUpVoted;
}
