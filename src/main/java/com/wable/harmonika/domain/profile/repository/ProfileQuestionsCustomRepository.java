package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;

public interface ProfileQuestionsCustomRepository {
    void updateProfileQuestionsByProfileId(Long profileQuestionId, ProfileQuestions profileQuestions);
}
