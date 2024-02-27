package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.QuestionTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDataDto {
    @NotBlank
    private Long id;

    @NotBlank
    private String sid;

    @NotBlank
    private String question;

    @NotBlank
    private QuestionTypes questionType;

    @NotBlank
    private List<String> answers;

    public QuestionDataDto(String sid, String question, QuestionTypes questionType, List<String> answers) {
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }
}
