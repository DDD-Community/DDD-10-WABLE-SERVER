package com.wable.harmonika.domain.profile.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.profile.entity.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wable.harmonika.domain.group.entity.QGroups.groups;
import static com.wable.harmonika.domain.profile.entity.QProfiles.profiles;
import static com.wable.harmonika.domain.user.entity.QUsers.users;

@Repository
public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    @Autowired
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
    public List<Profiles> getOtherProfileByUserAndGroupId(String toUserId, List<Long> groupId) {
        return jpaQueryFactory.selectFrom(profiles)
                .join(profiles.user, users)
                .join(profiles.group, groups)
                .where(profiles.user.userId.eq(toUserId))
                .where(profiles.group.id.in(groupId))
                .fetch();
    }

    @Override
    public List<Profiles> getProfileByGroupId(String userId, Long groupId) {
        return jpaQueryFactory.selectFrom(profiles)
                .join(profiles.user, users)
                .join(profiles.group, groups )
                .where(profiles.group.id.eq(groupId))
                .where(profiles.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Profiles> getProfileByUserId(String userId) {
        List<Profiles> result = jpaQueryFactory
                .selectFrom(profiles)
                .where(profiles.user.userId.eq(userId))
                .fetch();

        return result;
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
