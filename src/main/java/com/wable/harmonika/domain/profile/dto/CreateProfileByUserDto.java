package com.wable.harmonika.domain.profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateProfileByUserDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String name;


    @Pattern(regexp = "^(male|female)$", message = "invalid")
    private String gender;


    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "invalid_date_format")
    private LocalDate birth;


    @NotBlank
    private String nickName;

    private String profileImageUrl;

    @NotEmpty(message = "Questions list must not be empty")
    private List<QuestionDataDto> questions;

    public CreateProfileByUserDto(String userId, String name, String gender, LocalDate birth, String nickName, String profileImageUrl, List<QuestionDataDto> questions) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
        this.questions = questions;
    }
}