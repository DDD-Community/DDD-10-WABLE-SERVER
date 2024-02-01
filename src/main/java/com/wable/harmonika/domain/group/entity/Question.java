package com.wable.harmonika.domain.group.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String question;

    @Column(name = "has_selections")
    private boolean hasSelections;

    @ElementCollection
    private List<String> selections;

    // Getters and setters
}
