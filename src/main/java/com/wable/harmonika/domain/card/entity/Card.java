package com.wable.harmonika.domain.card.entity;

import com.wable.harmonika.domain.group.entity.Group;
import com.wable.harmonika.domain.user.entity.User;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "cards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardName sid;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    private String fromUserName;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    private String toUserName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String content;

    private boolean isVisible;
}
