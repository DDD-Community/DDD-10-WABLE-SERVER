package com.wable.harmonika.domain.group.repository;

import static com.wable.harmonika.domain.group.entity.QUserGroups.*;
import static com.wable.harmonika.domain.user.entity.QUsers.users;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.user.entity.Users;
import java.util.List;

public class UserGroupRepositoryCustomImpl implements UserGroupRepositoryCustom {

    private final JPAQueryFactory query;

    public UserGroupRepositoryCustomImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public List<Users> findAllUserWithPaging(Long groupId, String lastName, String searchName, int size) {
        return query.selectFrom(users)
                .leftJoin(userGroups)
                .on(users.userId.eq(userGroups.user.userId))
                .where(userGroups.group.id.eq(groupId)
                        .and(searchNameContains(searchName))
                        .and(lastNameGt(lastName)))
                .limit(size)
                .fetch();
    }

    private static BooleanExpression lastNameGt(String lastName) {
        if (lastName == null) {
            return null;
        }
        return users.name.gt(lastName);
    }

    private static BooleanExpression searchNameContains(String searchName) {
        if (searchName == null) {
            return null;
        }
        return users.name.contains(searchName);
    }
}
