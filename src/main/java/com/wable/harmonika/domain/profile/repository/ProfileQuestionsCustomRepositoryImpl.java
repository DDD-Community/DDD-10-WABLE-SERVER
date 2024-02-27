package com.wable.harmonika.domain.profile.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import static com.wable.harmonika.domain.profile.entity.QProfileQuestions.profileQuestions;

@Repository
public class ProfileQuestionsCustomRepositoryImpl implements ProfileQuestionsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProfileQuestionsCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public void updateProfileQuestionsByProfileId(Long id, ProfileQuestions data) {
        // TODO: 삭제에 대한 로직 추가 해야함
        if (id == null) {
            jpaQueryFactory.insert(profileQuestions)
                    .set(profileQuestions, data)
                    .execute();
        } else {
            jpaQueryFactory.update(profileQuestions)
                    .where(profileQuestions.id.eq(id))
                    .set(profileQuestions, data)
                    .execute();
        }
    }
}
