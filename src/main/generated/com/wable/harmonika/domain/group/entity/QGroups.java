package com.wable.harmonika.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroups is a Querydsl query type for Groups
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroups extends EntityPathBase<Groups> {

    private static final long serialVersionUID = 608974706L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroups groups = new QGroups("groups");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.wable.harmonika.domain.user.entity.QUsers owner;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroups(String variable) {
        this(Groups.class, forVariable(variable), INITS);
    }

    public QGroups(Path<? extends Groups> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroups(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroups(PathMetadata metadata, PathInits inits) {
        this(Groups.class, metadata, inits);
    }

    public QGroups(Class<? extends Groups> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.wable.harmonika.domain.user.entity.QUsers(forProperty("owner")) : null;
    }

}

