package com.wable.harmonika.domain.profile.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateProfileByGroupDto {
    @Null
    private String userId;

    @NotBlank
    private String name;

    @Pattern(regexp = "^(male|female)$", message = "invalid")
    private String gender;

    @NotNull(message = "empty")
    @Past(message = "invalid")
    private LocalDate birth;
    // TODO: 타입에 대한 에러 핸들링은 어떻게하냐..

    @NotBlank
    private String nickName;

    private String profileImageUrl;

    @NotEmpty(message = "Questions list must not be empty")
    private List<QuestionDataDto> questions;

    @NotNull(message = "empty")
    private Long groupId;

    @NotBlank
    private String token;

    public CreateProfileByGroupDto(String userId, String name, String gender, LocalDate birth, String nickName, String profileImageUrl, List<QuestionDataDto> questions, Long groupId, String token) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
        this.questions = questions;
        this.groupId = groupId;
        this.token = token;
    }
}
