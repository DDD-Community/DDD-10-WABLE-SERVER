package com.wable.harmonika.domain.group.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "group_questions")
public class GroupQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}

