package com.wable.harmonika.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestions is a Querydsl query type for Questions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestions extends EntityPathBase<Questions> {

    private static final long serialVersionUID = -276457745L;

    public static final QQuestions questions = new QQuestions("questions");

    public final com.wable.harmonika.global.entity.QBaseTimeEntity _super = new com.wable.harmonika.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath question = createString("question");

    public final EnumPath<QuestionTypes> questionType = createEnum("questionType", QuestionTypes.class);

    public final ListPath<String, StringPath> selections = this.<String, StringPath>createList("selections", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath sid = createString("sid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QQuestions(String variable) {
        super(Questions.class, forVariable(variable));
    }

    public QQuestions(Path<? extends Questions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestions(PathMetadata metadata) {
        super(Questions.class, metadata);
    }

}

