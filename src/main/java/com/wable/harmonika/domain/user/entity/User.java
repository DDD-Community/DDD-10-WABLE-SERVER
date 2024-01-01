package com.wable.harmonika.domain.user.entity;

import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        // pk 컬럼명은 따로 지정하는 것이 더 명확하다.
        @Column(name = "user_id", updatable = false)
        private Long id;

        // 소셜로그인으로 인해서 이메일 안 받을 수 있기에 추후 확인이 필요합니다.
        @NotNull(message = "이메일은 필수로 입력되어야 합니다.")
        @Email(message = "유효하지 않은 이메일 형식입니다.",
                regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
        private String email;

        @NotNull(message = "이름은 필수로 입력되어야 합니다.")
        private String name;

        // 소셜로그인으로 인해서 비밀번호를 안 받을 수 있기에 추후 확인이 필요합니다.
        @NotNull(message = "비밀번호는 필수로 입력되어야 합니다.")
        private String encodedPassword;

        @Builder
        public User(String email, String name, String encodedPassword) {
            this.email = email;
            this.name = name;
            this.encodedPassword = encodedPassword;
        }
}
