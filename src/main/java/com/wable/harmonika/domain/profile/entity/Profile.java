package com.wable.harmonika.domain.profile.entity;


import com.wable.harmonika.domain.group.entity.Group;
import com.wable.harmonika.domain.user.entity.User;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "profiles")
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String nickname;

    private String profileImageUrl;

    private String descriptions;
}
