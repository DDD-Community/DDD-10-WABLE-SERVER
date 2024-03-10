package com.wable.user_api.domain.profile.entity;


import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.global.entity.BaseTimeEntity;
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

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Nullable
    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Groups group;

    private String nickname;

    private String profileImageUrl;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<ProfileQuestions> profileQuestions;

    @Builder
    public Profiles(Long Id, Users user, Groups group, String nickname, String profileImageUrl) {
        this.id = Id;
        this.user = user;
        this.group = group;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
