package com.wable.harmonika.domain.card.entity;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private Users fromUser;

    private String fromUserName;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private Users toUser;

    private String toUserName;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private String content;

    private boolean isVisible;
}
