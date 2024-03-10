package com.wable.user_api.domain.group.entity;


import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserGroups extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Builder
    public UserGroups(Users user, Groups group, Position position) {
        this.user = user;
        this.group = group;
        this.position = position;
    }
}