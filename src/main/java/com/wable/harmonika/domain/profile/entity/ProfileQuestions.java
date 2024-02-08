package com.wable.harmonika.domain.profile.entity;

import com.wable.harmonika.domain.group.entity.QuestionTypes;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
public class ProfileQuestions extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profiles profile;

    private String sid;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionTypes questionType;

    @ElementCollection
    private List<String> answer;
}


