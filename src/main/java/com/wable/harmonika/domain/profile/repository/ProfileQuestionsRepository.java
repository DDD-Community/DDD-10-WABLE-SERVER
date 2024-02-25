package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.dto.QuestionDataDto;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileQuestionsRepository extends JpaRepository<ProfileQuestions, Long> {
    void updateProfileQuestionsByProfileId(ProfileQuestions questions, Long id);
}