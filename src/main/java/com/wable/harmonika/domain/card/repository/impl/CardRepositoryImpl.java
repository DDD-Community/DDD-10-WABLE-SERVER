package com.wable.harmonika.domain.card.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.repository.CardRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.wable.harmonika.domain.card.entity.QCards.cards;

public class CardRepositoryImpl implements CardRepositoryCustom {
    @Autowired
    private final JPAQueryFactory query;

    public CardRepositoryImpl(JPAQueryFactory query) {
        this.query = query;
    }

    public List<Cards> findAllCards(List<Long> groupIds, List<CardNames> sids, Long toUserId, Long fromUserId, Long lastId, Integer limit) {
        BooleanBuilder builder = new BooleanBuilder();

        if (lastId != 0) {
            builder.and(cards.id.lt(lastId)); // paging
        }

        if (toUserId != 0) {
            builder.and(cards.toUser.id.eq(toUserId));
        }

        if (fromUserId != 0) {
            builder.and(cards.fromUser.id.eq(fromUserId));
        }

//        // groupId가 있으면 필터
//        if (groupIds != null || groupIds.isEmpty()) {
//            builder.and(cards.group.id.in(groupIds));
//        }
        // sid가 있으면 필터
        if (sids != null || sids.isEmpty()) {
            builder.and(cards.sid.in(sids));
        }

        return query
                .selectFrom(cards)
                .where(builder)
                .orderBy(cards.id.desc())
                .limit(limit)
                .fetch();
    }
}
