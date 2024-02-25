package com.wable.harmonika.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.QuestionTypes;
import com.wable.harmonika.domain.profile.entity.Profiles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileResponseDto {
    private String userId;

    private String name;

    private String nickName;

    private String gender;

    private LocalDate birth;

    private String profileImageUrl;

    private List<QuestionDTO> questions;

    private GroupDTO group;

    public GetProfileResponseDto(Profiles profiles) {
        this.userId = profiles.getUser().getUserId();
        this.name = profiles.getUser().getName();
        this.gender = profiles.getUser().getGender();
        this.birth = profiles.getUser().getBirth();

        this.nickName = profiles.getNickname();
        this.profileImageUrl = profiles.getProfileImageUrl();

        if (profiles.getGroup() != null) {
            this.group = new GroupDTO(profiles.getGroup());
        }

        this.questions = profiles.getProfileQuestions().stream().map(
            profileQuestions -> new QuestionDTO(
                profileQuestions.getId(),
                profileQuestions.getSid(),
                profileQuestions.getQuestion(),
                profileQuestions.getQuestionType(),
                profileQuestions.getAnswers(),
                profileQuestions.getCreatedAt(),
                profileQuestions.getUpdatedAt()
            )
        ).toList();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GroupDTO {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        public GroupDTO(Groups group) {
            this.id = group.getId();
            this.name = group.getName();
            this.createdAt = group.getCreatedAt();
            this.updatedAt = group.getUpdatedAt();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class QuestionDTO {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("sid")
        private String sid;

        @JsonProperty("question")
        private String question;

        @JsonProperty("questionType")
        private QuestionTypes questionType;

        @JsonProperty("answers")
        private List<String> answers;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        public QuestionDTO(
            Long id,
            String sid,
            String question,
            QuestionTypes questionType,
            List<String> answers,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
        ) {
            this.id = id;
            this.sid = sid;
            this.question = question;
            this.questionType = questionType;
            this.answers = answers;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
