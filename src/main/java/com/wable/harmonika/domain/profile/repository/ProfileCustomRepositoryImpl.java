package com.wable.harmonika.domain.profile.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.profile.entity.Profiles;
import org.springframework.stereotype.Repository;

import static com.wable.harmonika.domain.profile.entity.QProfiles.profiles;

@Repository

public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProfileCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public boolean existsByUserIdAndGroupIdIsNull(String userId) {
        return jpaQueryFactory.selectFrom(profiles)
                .where(profiles.group.isNull())
                .where(profiles.user.userId.eq(userId))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByUserIdAndGroupId(String userId, Long groupId) {
        return jpaQueryFactory.selectFrom(profiles)
                .where(profiles.group.id.eq(groupId))
                .where(profiles.user.userId.eq(userId))
                .fetchFirst() != null;
    }

    @Override
    public Profiles getProfileByGroupId(Long groupId) {
        return jpaQueryFactory.selectFrom(profiles)
                .where(profiles.group.id.eq(groupId))
                .join(profiles.group)
                .fetchFirst();
    }

    @Override
    public Profiles getProfileByUserId(String uesrId) {
        return jpaQueryFactory.selectFrom(profiles)
                .where(profiles.user.userId.eq(uesrId))
                .join(profiles.group)
                .fetchAll()
                .join(profiles.profileQuestions)
                .fetchOne(); // Assuming you expect only one profile to be returned
    }

    @Override
    public Profiles findByUserIdAndGroupId(String userId, Long groupId) {
        return jpaQueryFactory.selectFrom(profiles)
                .where(profiles.user.userId.eq(userId))
                .where(profiles.group.id.eq(groupId))
                .fetchFirst();
    }

    @Override
    public void saveProfileById(Long id, Profiles profile) {
        jpaQueryFactory.update(profiles)
                .where(profiles.id.eq(id))
                .set(profiles, profile)
                .execute();
    }
}
