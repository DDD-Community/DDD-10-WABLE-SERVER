package com.wable.harmonika.domain.user.dto;

import com.wable.harmonika.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(name = "회원정보 응답 객체", description = "회원가입, 회원조회, 회원수정 응답에 사용되는 객체입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyUserResDto {

    private String email;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public MyUserResDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.createDate = user.getCreateDate();
        this.modifiedDate = user.getModifiedDate();
    }
}