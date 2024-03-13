package com.wable.user_api.domain.profile.repository.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.user_api.domain.profile.entity.ProfileQuestions;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.profile.repository.ProfileQuestionsCustomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import static com.wable.user_api.domain.profile.entity.QProfileQuestions.profileQuestions;

@Repository
public class ProfileQuestionsRepositoryImpl implements ProfileQuestionsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProfileQuestionsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public void updateProfileQuestionsByProfileId(Long profileQuestionId, ProfileQuestions data) {
        // TODO: 삭제에 대한 로직 추가 해야함
        if (profileQuestionId == null) {
            jpaQueryFactory.insert(profileQuestions)
                    .set(profileQuestions, data)
                    .execute();
        } else {
            jpaQueryFactory.update(profileQuestions)
                    .where(profileQuestions.id.eq(profileQuestionId))
                    .set(profileQuestions, data)
                    .execute();
        }
    }
}
