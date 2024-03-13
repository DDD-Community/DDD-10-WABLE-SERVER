package com.wable.user_api.domain.card.service;


import com.wable.user_api.domain.card.dto.*;
import com.wable.user_api.domain.card.entity.CardNames;
import com.wable.user_api.domain.card.entity.Cards;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CardService {
    void create(CardsRequest vo) throws Exception;
    Cards findById(Long bno) throws Exception;
    void update(UpdateCardsRequest vo) throws Exception;
    void delete(Long bno) throws Exception;
    ListCardsResponse findAllReceivedCards(ListCardsRequest request) throws Exception;

    ListCardsResponse findAllSentCards(ListCardsRequest request) throws Exception;

    List<CardsDto> findAllCardsByGroup(ListCardsByGroupRequest request) throws Exception;


}
