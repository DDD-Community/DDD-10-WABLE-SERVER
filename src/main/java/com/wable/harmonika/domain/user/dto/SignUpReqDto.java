package com.wable.harmonika.domain.user.dto;

import com.wable.harmonika.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "회원가입 요청 객체", description = "회원가입 요청 객체입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpReqDto {

    @Schema(description = "이메일", example = "test@gmail.com")
    @NotBlank(message = "이메일은 필수로 입력되어야 합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @Schema(description = "이름", example = "테스트계정")
    @NotBlank(message = "이름은 필수로 입력되어야 합니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9](?=\\S+$).{2,10}$", message = "닉네임은 2~10자의 한글, 영어, 숫자로 공백 없이 작성되어야 합니다.")
    private String name;

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank(message = "비밀번호는 필수로 입력되어야 합니다.")
    @Pattern(message = "비밀번호는 4~16자에 영어, 숫자, 특수문자가 포함된 형태로 공백 없이 작성되어야 합니다.", regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}")
    private String password;

    @Builder
    public SignUpReqDto(String email, String name, String password) {
        this.name = name;
        this.password = password;
    }
}

