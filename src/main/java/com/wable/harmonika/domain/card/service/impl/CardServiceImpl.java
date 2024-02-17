package com.wable.harmonika.domain.card.service.impl;

import com.wable.harmonika.domain.card.dto.CardsRequest;
import com.wable.harmonika.domain.card.dto.ListCardsRequest;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.repository.CardRepository;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CardServiceImpl implements CardService {
    Map<String, Object> firstData = new HashMap<>();
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(CardsRequest vo) throws Exception {
        // TODO : token에서 까서 넣기
        Long fromUserId = 2L;
        List<Users> users = userRepository.findAllById(List.of(vo.getToUserId(), fromUserId));

        Users toUser = users.stream()
                .filter(user -> user.getId().equals(vo.getToUserId()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("User not found with ID: " + vo.getToUserId()));
        Users fromUser = users.stream()
                .filter(user -> user.getId().equals(fromUserId))
                .findFirst().orElseThrow(() -> new NoSuchElementException("User not found with ID: " + vo.getToUserId()));

        System.out.println("vo = " + vo);

        // TODO : group repo 완료시 가져와서 넣기
        Groups groups = new Groups();
        // TODO : UserName 따로 필요없어보임
        Cards cards = new Cards.Builder()
                .sid(vo.getSid())
                .content(vo.getContent())
                .isVisible(vo.isVisible())
                .build();

        cards.setFromUser(fromUser);
        cards.setToUser(toUser);

        cardRepository.save(cards);
    }

    @Override
    public Cards findById(Long bno) throws Exception {
        return cardRepository.findById(bno).get();
    }

    @Override
    public void update(Cards vo) throws Exception {

    }

    @Override
    public void delete(Long bno) throws Exception {

    }

    @Override
    public List<Cards> findAllReceivedCards(ListCardsRequest request) throws Exception {

        Long userId = 2L;

        return cardRepository.findAllCards(
                request.getGroupIds(),
                request.getSids(),
                userId,
                0L,
                request.getLastId(),
                request.getSize()
        );
    }

    @Override
    public List<Cards> findAllSentCards(ListCardsRequest request) throws Exception {

        Long userId = 2L;

        return cardRepository.findAllCards(
                request.getGroupIds(),
                request.getSids(),
                0L,
                userId,
                request.getLastId(),
                request.getSize()
        );
    }

    @Override
    public List<Cards> findAllCardsByGroup(ListCardsRequest request) throws Exception {
        return cardRepository.findAllCards(
                request.getGroupIds(),
                request.getSids(),
                0L,
                0L,
                request.getLastId(),
                request.getSize()
        );
    }
}
