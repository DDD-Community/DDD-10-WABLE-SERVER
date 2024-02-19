package com.wable.harmonika.domain.card.service.impl;

import com.wable.harmonika.domain.card.dto.CardsRequest;
import com.wable.harmonika.domain.card.dto.ListCardsRequest;
import com.wable.harmonika.domain.card.dto.UpdateCardsRequest;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.repository.CardRepository;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.group.GroupRepository;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import org.apache.catalina.Group;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CardServiceImpl implements CardService {
    Map<String, Object> firstData = new HashMap<>();
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;


    @Override
    public void create(CardsRequest vo, Users fromUser) throws Exception {
        Users user = userRepository.save(new Users(fromUser.getUserId(), "이름", LocalDate.of(1990, 1, 1)));
        Users owner = userRepository.save(new Users("abc", "이름", LocalDate.of(1990, 1, 1)));
        Groups group = groupRepository.save(new Groups("그룹명", owner));
        Cards cards = new Cards(vo.getSid(), vo.getContent());


        cards.setFromUser(user);
        cards.setToUser(owner);
        cards.setGroup(new Groups(group.getId()));

        cardRepository.save(cards);
    }

    @Override
    public Cards findById(Long bno) throws Exception {
        return cardRepository.findById(bno).get();
    }

    @Override
    public void update(UpdateCardsRequest vo) throws Exception {
        System.out.println("vo = " + vo);
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
