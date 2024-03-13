package com.wable.user_api.domain.card.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.user_api.domain.card.dto.FindAllCardsParam;
import com.wable.user_api.domain.card.entity.CardNames;
import com.wable.user_api.domain.card.entity.Cards;
import com.wable.user_api.domain.card.repository.CardRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.wable.user_api.domain.card.entity.QCards.cards;

public class CardRepositoryImpl implements CardRepositoryCustom {

    @Autowired
    private final JPAQueryFactory query;

    public CardRepositoryImpl(JPAQueryFactory query) {
        this.query = query;
    }

    public List<Cards> findAllCards(FindAllCardsParam param) {
        BooleanBuilder builder = new BooleanBuilder();

        if (param.getLastId() != null && param.getLastId() != 0) {
            builder.and(cards.id.lt(param.getLastId()));
        }

        if (param.getToUserId() != null && !param.getToUserId().isEmpty()) {
            builder.and(cards.toUser.userId.eq(param.getToUserId()));
        }

        if (param.getFromUserId() != null && !param.getFromUserId().isEmpty()) {
            builder.and(cards.fromUser.userId.eq(param.getFromUserId()));
        }

        if (param.getGroupIds() != null && !param.getGroupIds().isEmpty()) {
            builder.and(cards.group.id.in(param.getGroupIds()));
        }

        if (param.getSids() != null && !param.getSids().isEmpty()) {
            builder.and(cards.sid.in(param.getSids()));
        }

        // 쿼리 실행
        return query
                .selectFrom(cards)
                .where(builder)
                .orderBy(cards.id.desc())
                .limit(param.getSize())
                .fetch();
    }

    public Long countBy(FindAllCardsParam param) {
        BooleanBuilder builder = new BooleanBuilder();

        if (param.getLastId() != null && param.getLastId() != 0) {
            builder.and(cards.id.lt(param.getLastId()));
        }

        if (param.getToUserId() != null && !param.getToUserId().isEmpty()) {
            builder.and(cards.toUser.userId.eq(param.getToUserId()));
        }

        if (param.getFromUserId() != null && !param.getFromUserId().isEmpty()) {
            builder.and(cards.fromUser.userId.eq(param.getFromUserId()));
        }

        if (param.getGroupIds() != null && !param.getGroupIds().isEmpty()) {
            builder.and(cards.group.id.in(param.getGroupIds()));
        }

        if (param.getSids() != null && !param.getSids().isEmpty()) {
            builder.and(cards.sid.in(param.getSids()));
        }

        // 쿼리 실행
        return query
                .selectFrom(cards)
                .where(builder)
                .fetchCount();
    }
}
