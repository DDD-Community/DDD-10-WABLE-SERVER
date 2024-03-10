package com.wable.user_api.domain.group.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "group_questions")
public class GroupQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions question;

    private boolean required;

    public GroupQuestion(Long id, Groups group, Questions question, boolean required) {
        this.id = id;
        this.group = group;
        this.question = question;
        this.required = required;
    }

    public GroupQuestion() {

    }
}
