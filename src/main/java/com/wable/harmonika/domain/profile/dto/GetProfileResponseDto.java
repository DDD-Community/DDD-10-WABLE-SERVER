package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.QuestionTypes;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetProfileResponseDto {
    private String userId;

    private String name;

    private String nickName;

    private String gender;

    private LocalDate birth;

    private String profileImageUrl;

    private List<QuestionDTO> questions;

    public GetProfileResponseDto(Profiles profiles) {
        this.userId = profiles.getUser().getUserId();
        this.name = profiles.getUser().getName();
        this.gender = profiles.getUser().getGender();
        this.birth = profiles.getUser().getBirth();

        this.nickName = profiles.getNickname();
        this.profileImageUrl = profiles.getProfileImageUrl();

        for (ProfileQuestions profileQuestion : profiles.getProfileQuestions()) {
            QuestionDTO questionDTO = new QuestionDTO(
                    profileQuestion.getSid(),
                    profileQuestion.getQuestion(),
                    profileQuestion.getQuestionType(),
                    profileQuestion.getAnswers(),
                    profileQuestion.getCreatedAt(),
                    profileQuestion.getUpdatedAt()
            );
            this.questions.add(questionDTO);
        }
    }

    public static class QuestionDTO {
        private String sid;
        private String question;
        private QuestionTypes questionType;
        private List<String> answers;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public QuestionDTO(
            String sid,
            String question,
            QuestionTypes questionType,
            List<String> answers,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
        ) {
            this.sid = sid;
            this.question = question;
            this.questionType = questionType;
            this.answers = answers;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
