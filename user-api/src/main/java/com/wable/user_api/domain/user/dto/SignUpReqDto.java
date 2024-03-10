package com.wable.user_api.domain.user.dto;

import com.wable.user_api.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(name = "회원가입 요청 객체", description = "회원가입 요청 객체입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpReqDto {

    @Schema(description = "이름", example = "테스트계정")
    @NotBlank(message = "이름은 필수로 입력되어야 합니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9](?=\\S+$).{2,10}$", message = "닉네임은 2~10자의 한글, 영어, 숫자로 공백 없이 작성되어야 합니다.")
    private String name;

    @Schema(description = "생년월일", example = "1990-01-01")
    private LocalDate birth;

    @Builder
    public SignUpReqDto(String name, LocalDate birth) {
        this.name = name;
        this.birth= birth;
    }
}

