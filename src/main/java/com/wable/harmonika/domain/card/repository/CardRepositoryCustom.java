package com.wable.harmonika.domain.card.repository;

import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;

import java.util.List;

public interface CardRepositoryCustom {
    List<Cards> findAllCards(List<Long> groupIds, List<CardNames> sids, Long toUserId, Long fromUserId, Long lastId, Integer limit);
}
