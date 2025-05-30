package com.project.Discussly.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddQuestionDto {
    @NotBlank
    private String questionTitle;
    private String questionText;
    private List<String> tags;
}
