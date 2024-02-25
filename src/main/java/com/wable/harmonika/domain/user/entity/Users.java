package com.wable.harmonika.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import lombok.*;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(targetEntity = Groups.class)
    private List<Groups> groups;

    @Builder
    public Users(String userId, String gender, String name, LocalDate birth) {
        this.userId = userId;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
    }
}
