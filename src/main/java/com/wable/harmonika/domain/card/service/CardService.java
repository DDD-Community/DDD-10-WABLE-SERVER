package com.wable.harmonika.domain.card.service;


import com.wable.harmonika.domain.card.dto.CardsRequest;
import com.wable.harmonika.domain.card.dto.ListCardsRequest;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.user.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CardService {
    public void create(CardsRequest vo, Users fromUser) throws Exception;
    public Cards findById(Long bno) throws Exception;
    public void update(Cards vo) throws Exception;
    public void delete(Long bno) throws Exception;
    public List<Cards> findAllReceivedCards(ListCardsRequest request) throws Exception;

    public List<Cards> findAllSentCards(ListCardsRequest request) throws Exception;

    public List<Cards> findAllCardsByGroup(ListCardsRequest request) throws Exception;


}
