package com.wable.harmonika.domain.card.service;


import com.wable.harmonika.domain.card.dto.*;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.user.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CardService {
    public void create(CardsRequest vo) throws Exception;
    public Cards findById(Long bno) throws Exception;
    public void update(UpdateCardsRequest vo) throws Exception;
    public void delete(Long bno) throws Exception;
    public ListCardsResponse findAllReceivedCards(ListCardsRequest request) throws Exception;

    public ListCardsResponse findAllSentCards(ListCardsRequest request) throws Exception;

    public List<CardsDto> findAllCardsByGroup(ListCardsByGroupRequest request) throws Exception;


}
