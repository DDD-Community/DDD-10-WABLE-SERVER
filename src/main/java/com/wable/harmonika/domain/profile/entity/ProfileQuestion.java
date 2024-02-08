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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    private String sid;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ElementCollection
    private List<String> answer;
}


