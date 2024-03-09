package com.wable.harmonika.domain.card.service;


import com.wable.harmonika.domain.card.dto.*;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.user.entity.Users;
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
