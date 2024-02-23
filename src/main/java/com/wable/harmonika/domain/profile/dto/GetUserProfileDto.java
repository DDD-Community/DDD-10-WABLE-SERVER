package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetUserProfileDto {
    private String userId;

    private String name;

    private String nickName;

    private String gender;

    private LocalDate birth;

    private String profileImageUrl;

    private List<QuestionDTO> questions;

    public GetUserProfileDto(Users user, Profiles profiles, List<ProfileQuestionDTO> profileQuestions) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.birth = user.getBirth();

        this.nickName = profiles.getNickname();
        this.profileImageUrl = profiles.getProfileImageUrl();

        for (ProfileQuestionDTO profileQuestion : profileQuestions) {
            QuestionDTO questionDTO = new QuestionDTO(profileQuestion.getQuestion(), profileQuestion.getAnswers());
            this.questions.add(questionDTO);
        }
    }

    public static class QuestionDTO {
        private String key;
        private Object value;

        public QuestionDTO(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
