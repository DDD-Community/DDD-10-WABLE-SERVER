package com.wable.harmonika.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGroups is a Querydsl query type for UserGroups
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserGroups extends EntityPathBase<UserGroups> {

    private static final long serialVersionUID = 681102365L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGroups userGroups = new QUserGroups("userGroups");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGroups group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath position = createString("position");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.wable.harmonika.domain.user.entity.QUsers user;

    public QUserGroups(String variable) {
        this(UserGroups.class, forVariable(variable), INITS);
    }

    public QUserGroups(Path<? extends UserGroups> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGroups(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGroups(PathMetadata metadata, PathInits inits) {
        this(UserGroups.class, metadata, inits);
    }

    public QUserGroups(Class<? extends UserGroups> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroups(forProperty("group"), inits.get("group")) : null;
        this.user = inits.isInitialized("user") ? new com.wable.harmonika.domain.user.entity.QUsers(forProperty("user")) : null;
    }

}

