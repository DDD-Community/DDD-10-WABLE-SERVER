package com.wable.user_api.domain.card.repository;

import com.wable.user_api.domain.card.dto.FindAllCardsParam;
import com.wable.user_api.domain.card.entity.Cards;
import com.wable.user_api.domain.group.entity.Groups;

import java.util.List;

public interface CardRepositoryCustom {
    List<Cards> findAllCards(FindAllCardsParam param);

    Long countBy(FindAllCardsParam param);

}
