package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.QuestionTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProfileQuestionDTO {
    private String sid;
    private String question;
    private QuestionTypes questionType;
    private List<String> answers;

    public ProfileQuestionDTO(String sid, String question, QuestionTypes questionType, List<String> answers) {
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }
}
