package com.wable.user_api.domain.card.entity;

import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
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

    @ManyToOne
    @JoinColumn(name = "from_user_profile_id")
    private Profiles fromUserProfile;

    @ManyToOne
    @JoinColumn(name = "to_user_profile_id")
    private Profiles toUserProfile;

    private String content;

    @Builder
    public Cards(Long id, CardNames sid, String content, Users fromUser, Users toUser, Long groupId, Profiles fromUserProfile, Profiles toUserProfile) {
        this.id = id;
        this.sid = sid;
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.group = Groups.builder().id(groupId).build();
        this.fromUserProfile = fromUserProfile;
        this.toUserProfile = toUserProfile;
    }

    public Cards updateContent(String content) {
        this.content = content;
        return this;
    }

    public Cards updateSid(CardNames sid) {
        this.sid = sid;
        return this;
    }
}
