package com.wable.harmonika.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 1730494498L;

    public static final QUsers users = new QUsers("users");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gender = createString("gender");

    public final ListPath<com.wable.harmonika.domain.group.entity.Groups, com.wable.harmonika.domain.group.entity.QGroups> groups = this.<com.wable.harmonika.domain.group.entity.Groups, com.wable.harmonika.domain.group.entity.QGroups>createList("groups", com.wable.harmonika.domain.group.entity.Groups.class, com.wable.harmonika.domain.group.entity.QGroups.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.wable.harmonika.domain.profile.entity.Profiles, com.wable.harmonika.domain.profile.entity.QProfiles> profiles = this.<com.wable.harmonika.domain.profile.entity.Profiles, com.wable.harmonika.domain.profile.entity.QProfiles>createList("profiles", com.wable.harmonika.domain.profile.entity.Profiles.class, com.wable.harmonika.domain.profile.entity.QProfiles.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userId = createString("userId");

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

