package com.wable.user_api.domain.profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateProfileDto {
    @Null
    private String userId;

    @NotBlank
    private String name;

    @Pattern(regexp = "^(남성|여성)$", message = "invalid")
    private String gender;

    @NotNull(message = "empty")
    @Past(message = "invalid")
    private LocalDate birth;

    @NotBlank
    private String nickname;

    private String profileImageUrl;

    @Valid
    private List<QuestionDataDto> questions;

    private Long groupId = null;

    public UpdateProfileDto(String userId, String name, String gender, LocalDate birth, String nickname, String profileImageUrl, List<QuestionDataDto> questions, Long groupId) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.questions = questions;
        this.groupId = groupId;
    }
}
