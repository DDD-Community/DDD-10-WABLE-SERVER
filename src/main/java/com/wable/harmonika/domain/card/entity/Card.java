package com.wable.harmonika.domain.card.entity;

import com.wable.harmonika.domain.group.entity.Group;
import com.wable.harmonika.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @Column(name = "from_user_name")
    private String fromUserName;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(name = "to_user_name")
    private String toUserName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private CardType type;

    private String content;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters and setters
}