package com.wable.harmonika.domain.group.entity;

import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Questions extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionNames sid;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionTypes questionType;

    @ElementCollection
    private List<String> selections;

    public Questions(Long id, QuestionNames sid, String question, QuestionTypes questionType,
            List<String> selections) {
        this.id = id;
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.selections = selections;
    }

    public Questions() {

    }
}
