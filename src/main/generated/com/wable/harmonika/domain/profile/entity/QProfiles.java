package com.wable.harmonika.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfiles is a Querydsl query type for Profiles
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfiles extends EntityPathBase<Profiles> {

    private static final long serialVersionUID = 1274523826L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfiles profiles = new QProfiles("profiles");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath descriptions = createString("descriptions");

    public final com.wable.harmonika.domain.group.entity.QGroups group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.wable.harmonika.domain.user.entity.QUsers user;

    public QProfiles(String variable) {
        this(Profiles.class, forVariable(variable), INITS);
    }

    public QProfiles(Path<? extends Profiles> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfiles(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfiles(PathMetadata metadata, PathInits inits) {
        this(Profiles.class, metadata, inits);
    }

    public QProfiles(Class<? extends Profiles> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new com.wable.harmonika.domain.group.entity.QGroups(forProperty("group"), inits.get("group")) : null;
        this.user = inits.isInitialized("user") ? new com.wable.harmonika.domain.user.entity.QUsers(forProperty("user")) : null;
    }

}

