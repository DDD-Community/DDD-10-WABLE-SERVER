package com.wable.harmonika.domain.profile.dto;

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

    @Pattern(regexp = "^(male|female)$", message = "invalid")
    private String gender;

    @NotNull(message = "empty")
    @Past(message = "invalid")
    private LocalDate birth;

    @NotBlank
    private String nickName;

    private String profileImageUrl;

    @Valid
    private List<QuestionDataDto> questions;

    private Long groupId = null;

    public UpdateProfileDto(String userId, String name, String gender, LocalDate birth, String nickName, String profileImageUrl, List<QuestionDataDto> questions, Long groupId) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
        this.questions = questions;
        this.groupId = groupId;
    }
}
