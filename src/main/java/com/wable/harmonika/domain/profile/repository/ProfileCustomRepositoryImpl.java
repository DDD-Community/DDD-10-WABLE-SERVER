package com.wable.harmonika.domain.profile.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
