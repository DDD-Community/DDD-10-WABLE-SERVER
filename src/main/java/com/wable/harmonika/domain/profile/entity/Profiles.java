package com.wable.harmonika.domain.profile.entity;


import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "profiles", indexes = @Index(name = "uniqueMulitIndex", columnList = "user_id, group_id", unique = true))
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
}
