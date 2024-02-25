package com.wable.harmonika.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupQuestion is a Querydsl query type for GroupQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupQuestion extends EntityPathBase<GroupQuestion> {

    private static final long serialVersionUID = 1699961895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupQuestion groupQuestion = new QGroupQuestion("groupQuestion");

    public final QGroups group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestions question;

    public QGroupQuestion(String variable) {
        this(GroupQuestion.class, forVariable(variable), INITS);
    }

    public QGroupQuestion(Path<? extends GroupQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupQuestion(PathMetadata metadata, PathInits inits) {
        this(GroupQuestion.class, metadata, inits);
    }

    public QGroupQuestion(Class<? extends GroupQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroups(forProperty("group"), inits.get("group")) : null;
        this.question = inits.isInitialized("question") ? new QQuestions(forProperty("question")) : null;
    }

}

