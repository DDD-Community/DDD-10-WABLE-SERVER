package com.wable.user_api.domain.profile.entity;

import com.wable.user_api.domain.group.entity.QuestionNames;
import com.wable.user_api.domain.group.entity.QuestionTypes;
import com.wable.user_api.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class ProfileQuestions extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profiles profile;

    private String sid;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionTypes questionType;

    @ElementCollection
    private List<String> answers;

    @Builder
    public ProfileQuestions(Long id, Profiles profile, String sid, String question, QuestionTypes questionType, List<String> answers) {
        this.id = id;
        this.profile = profile;
        this.sid = sid;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }
}


