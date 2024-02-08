package com.wable.harmonika.domain.profile.entity;

import com.wable.harmonika.domain.group.entity.QuestionType;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
@Table(name = "profile_questions")
public class ProfileQuestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @Column(name = "sid")
    private String sid;

    @Column(name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Column(name = "answers")
    @ElementCollection
    private List<String> answer;
}


