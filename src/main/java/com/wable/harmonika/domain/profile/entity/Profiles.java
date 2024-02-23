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

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "profiles", indexes = @Index(name = "profiles_user_group_unique_index", columnList = "user_id, group_id", unique = true))
public class Profiles extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private String nickname;

    private String profileImageUrl;

    @Builder
    public Profiles(Users user, Groups group, String nickname, String profileImageUrl) {
        this.user = user;
        this.group = group;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
