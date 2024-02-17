package com.wable.harmonika.domain.group.entity;


import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "`groups`")
public class Groups extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    public Groups(Long id, String name, Users owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }
}
