package com.wable.harmonika.domain.user.entity;

import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    private String name;

    private LocalDate birth;


    public Users(Long id, String userId, String name, LocalDate birth) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.birth = birth;
    }

    @Builder
    public Users(String userId) {
        this.userId = userId;
    }
}
