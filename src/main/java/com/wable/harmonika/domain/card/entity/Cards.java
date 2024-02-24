package com.wable.harmonika.domain.card.entity;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cards")
public class Cards extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    private Users fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    private Users toUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private String content;

    public Cards(CardNames sid, String content) {
        super();
    }
}
