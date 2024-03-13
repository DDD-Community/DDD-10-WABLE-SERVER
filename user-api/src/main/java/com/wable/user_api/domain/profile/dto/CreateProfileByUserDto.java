package com.wable.user_api.domain.profile.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateProfileByUserDto {
    @Null
    private String userId;

    @NotBlank
    private String name;

    @Pattern(regexp = "^(남성|여성)$", message = "invalid")
    private String gender;

    @NotNull(message = "empty")
    @Past(message = "invalid")
    private LocalDate birth;
    // TODO: 타입에 대한 에러 핸들링은 어떻게하냐..

    @NotBlank
    private String nickname;

    private String profileImageUrl;

    @NotEmpty(message = "Questions list must not be empty")
    private List<QuestionDataDto> questions;

    public CreateProfileByUserDto(String userId, String name, String gender, LocalDate birth, String nickname, String profileImageUrl, List<QuestionDataDto> questions) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.questions = questions;
    }
}