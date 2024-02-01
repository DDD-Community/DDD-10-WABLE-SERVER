package com.wable.harmonika.domain.profile.entity;

import com.wable.harmonika.domain.group.entity.QuestionType;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.util.List;


@Entity
public class ProfileQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    private String sid;

    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @ElementCollection
    private List<String> answers;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Getters and Setters
}


