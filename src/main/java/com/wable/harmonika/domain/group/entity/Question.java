package com.wable.harmonika.domain.group.entity;

import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "questions")
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sid;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ElementCollection
    private List<String> selections;
}
