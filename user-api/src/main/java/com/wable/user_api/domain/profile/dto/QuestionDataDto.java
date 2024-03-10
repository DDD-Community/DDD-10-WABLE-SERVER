package com.wable.user_api.domain.profile.dto;

import com.wable.user_api.domain.group.entity.QuestionNames;
import com.wable.user_api.domain.group.entity.QuestionTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class QuestionDataDto {
    @NotNull
    private Long id;

    @NotBlank
    private String sid;

    @NotBlank
    private String question;

    @NotNull
    private QuestionTypes questionType;

    @NotEmpty
    private List<String> answers;

    public QuestionDataDto(Long id, String sid, String question, QuestionTypes questionType, List<String> answers) {
        this.id = id;
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }
}
