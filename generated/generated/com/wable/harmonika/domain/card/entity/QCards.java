package com.wable.harmonika.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCards is a Querydsl query type for Cards
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCards extends EntityPathBase<Cards> {

    private static final long serialVersionUID = 334479960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCards cards = new QCards("cards");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.wable.harmonika.domain.user.entity.QUsers fromUser;

    public final com.wable.harmonika.domain.profile.entity.QProfiles fromUserProfile;

    public final com.wable.harmonika.domain.group.entity.QGroups group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<CardNames> sid = createEnum("sid", CardNames.class);

    public final com.wable.harmonika.domain.user.entity.QUsers toUser;

    public final com.wable.harmonika.domain.profile.entity.QProfiles toUserProfile;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCards(String variable) {
        this(Cards.class, forVariable(variable), INITS);
    }

    public QCards(Path<? extends Cards> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCards(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCards(PathMetadata metadata, PathInits inits) {
        this(Cards.class, metadata, inits);
    }

    public QCards(Class<? extends Cards> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUser = inits.isInitialized("fromUser") ? new com.wable.harmonika.domain.user.entity.QUsers(forProperty("fromUser")) : null;
        this.fromUserProfile = inits.isInitialized("fromUserProfile") ? new com.wable.harmonika.domain.profile.entity.QProfiles(forProperty("fromUserProfile"), inits.get("fromUserProfile")) : null;
        this.group = inits.isInitialized("group") ? new com.wable.harmonika.domain.group.entity.QGroups(forProperty("group"), inits.get("group")) : null;
        this.toUser = inits.isInitialized("toUser") ? new com.wable.harmonika.domain.user.entity.QUsers(forProperty("toUser")) : null;
        this.toUserProfile = inits.isInitialized("toUserProfile") ? new com.wable.harmonika.domain.profile.entity.QProfiles(forProperty("toUserProfile"), inits.get("toUserProfile")) : null;
    }

}

