package com.wable.user_api.domain.profile.repository;

import com.wable.user_api.domain.profile.dto.QuestionDataDto;
import com.wable.user_api.domain.profile.entity.ProfileQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileQuestionsRepository extends JpaRepository<ProfileQuestions, Long>, ProfileQuestionsCustomRepository {
}