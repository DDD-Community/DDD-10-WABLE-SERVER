package com.wable.user_api.domain.profile.repository;

import com.wable.user_api.domain.profile.entity.ProfileQuestions;
import com.wable.user_api.domain.profile.entity.Profiles;

public interface ProfileQuestionsCustomRepository {
    void updateProfileQuestionsByProfileId(Long profileQuestionId, ProfileQuestions profileQuestions);
}
