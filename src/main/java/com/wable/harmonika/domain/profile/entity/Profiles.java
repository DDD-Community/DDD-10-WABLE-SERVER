package com.wable.harmonika.domain.profile.entity;


import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "profiles", indexes = @Index(name = "profiles_user_group_unique_index", columnList = "user_id, group_id", unique = true))
public class Profiles extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "group_id")
    private Groups group;

    private String nickname;

    private String profileImageUrl;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<ProfileQuestions> profileQuestions;

    @Builder
    public Profiles(Users user, Groups group, String nickname, String profileImageUrl) {
        this.user = user;
        this.group = group;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
