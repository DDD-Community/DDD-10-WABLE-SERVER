package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.QuestionTypes;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDataDto {
    private String sid;
    private String question;
    private QuestionTypes questionType;
    private List<String> answers;

    public QuestionDataDto(String sid, String question, QuestionTypes questionType, List<String> answers) {
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }
}
