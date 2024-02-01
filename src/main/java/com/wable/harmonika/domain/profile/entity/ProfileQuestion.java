package com.wable.harmonika.domain.profile.entity;

import com.wable.harmonika.domain.group.entity.QuestionType;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "profile_questions")
public class ProfileQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String question;

    @Column(name = "has_selections")
    private boolean hasSelections;

    @ElementCollection
    private List<String> answers;

    // Getters and setters
}


