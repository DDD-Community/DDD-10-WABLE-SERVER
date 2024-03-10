package com.wable.user_api.domain.group.dto;

import com.wable.user_api.domain.group.entity.QuestionNames;
import com.wable.user_api.domain.group.entity.QuestionTypes;
import com.wable.user_api.domain.group.entity.Questions;
import lombok.Getter;

@Getter
public class QuestionsResponse {

    private Long id;
    private QuestionNames sid;
    private String question;
    private QuestionTypes questionType;
    private boolean isRequired;

    public QuestionsResponse(Long id, QuestionNames sid, String question,
            QuestionTypes questionType,
            boolean isRequired) {
        this.id = id;
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.isRequired = isRequired;
    }

    public static QuestionsResponse of(Questions questions, boolean isRequired) {
        return new QuestionsResponse(questions.getId(), questions.getSid(), questions.getQuestion(),
                questions.getQuestionType(), isRequired);
    }
}
