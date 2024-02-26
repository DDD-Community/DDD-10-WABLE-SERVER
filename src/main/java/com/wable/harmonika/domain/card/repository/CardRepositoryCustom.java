package com.wable.harmonika.domain.card.repository;

import com.wable.harmonika.domain.card.dto.FindAllCardsParam;
import com.wable.harmonika.domain.card.entity.Cards;

import java.util.List;

public interface CardRepositoryCustom {
    List<Cards> findAllCards(FindAllCardsParam param);
}
