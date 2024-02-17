package com.wable.harmonika.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileQuestions is a Querydsl query type for ProfileQuestions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfileQuestions extends EntityPathBase<ProfileQuestions> {

    private static final long serialVersionUID = -78224788L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileQuestions profileQuestions = new QProfileQuestions("profileQuestions");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    public final ListPath<String, StringPath> answer = this.<String, StringPath>createList("answer", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProfiles profile;

    public final StringPath question = createString("question");

    public final EnumPath<com.wable.harmonika.domain.group.entity.QuestionTypes> questionType = createEnum("questionType", com.wable.harmonika.domain.group.entity.QuestionTypes.class);

    public final StringPath sid = createString("sid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProfileQuestions(String variable) {
        this(ProfileQuestions.class, forVariable(variable), INITS);
    }

    public QProfileQuestions(Path<? extends ProfileQuestions> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileQuestions(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileQuestions(PathMetadata metadata, PathInits inits) {
        this(ProfileQuestions.class, metadata, inits);
    }

    public QProfileQuestions(Class<? extends ProfileQuestions> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new QProfiles(forProperty("profile"), inits.get("profile")) : null;
    }

}

