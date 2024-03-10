package com.wable.user_api.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.group.entity.UserGroups;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import lombok.*;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="user_id" , unique=true)
    private String userId;

    private String gender;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    private String name;

    private LocalDate birth;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Profiles> profiles;

    @OneToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
    private List<Groups> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserGroups> userGroups;

    @Builder
    public Users(String userId, String gender, String name, LocalDate birth) {
        this.userId = userId;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
    }
}
