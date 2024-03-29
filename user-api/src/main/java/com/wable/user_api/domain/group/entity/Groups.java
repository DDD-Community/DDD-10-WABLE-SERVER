package com.wable.user_api.domain.group.entity;


import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "`groups`")
public class Groups extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name="name" , unique=true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private Users owner;

    @Builder
    public Groups(Long id, String name, Users owner) {
        this.name = name;
        this.owner = owner;
    }

    public Groups(Long id) {
        this.id = id;
    }

    public Groups(String name, Users owner)  {
        this.name = name;
        this.owner = owner;
    }
}
