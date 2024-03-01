package com.wable.harmonika.domain.card.service.impl;

import com.wable.harmonika.domain.card.dto.*;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.repository.CardRepository;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.exception.UserNotFoundException;
import com.wable.harmonika.domain.user.repository.UserRepository;
import com.wable.harmonika.global.error.Error;
import com.wable.harmonika.global.error.exception.InvalidException;
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
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public void create(CardsRequest vo) throws Exception {
        // 해당 그룹의 프로필들이 있는지 확인
        Profiles ToUserProfile = profileRepository.findByUserIdAndGroupId(vo.getToUserId(), vo.getGroupId());
        if (ToUserProfile == null) {
            throw new InvalidException("user_id", vo.getToUserId(), Error.PROFILE_NOT_FOUND);
        }

        Profiles FromUserProfile = profileRepository.findByUserIdAndGroupId(vo.getFromUserId(), vo.getGroupId());
        if (FromUserProfile == null) {
            throw new InvalidException("user_id", vo.getToUserId(), Error.PROFILE_NOT_FOUND);
        }

        Cards cards = Cards.builder()
                .sid(vo.getSid())
                .content(vo.getContent())
                .fromUser(userRepository.findByUserId(vo.getFromUserId()).orElseThrow(() -> new UserNotFoundException("userId", vo.getFromUserId())))
                .toUser(userRepository.findByUserId(vo.getToUserId()).orElseThrow(() -> new UserNotFoundException("userId", vo.getToUserId())))
                .groupId(vo.getGroupId())
                .fromUserProfile(FromUserProfile)
                .toUserProfile(profileRepository.findByUserIdAndGroupId(vo.getToUserId(), vo.getGroupId()))
                .build();

        cardRepository.save(cards);
    }

    @Override
    public Cards findById(Long bno) throws Exception {
        return cardRepository.findById(bno).get();
    }

    @Override
    public void update(UpdateCardsRequest vo) throws Exception {
        Cards card = cardRepository.findById(vo.getCardId())
                .orElseThrow(() -> new InvalidException("card_id", vo.getCardId(), Error.CARD_NOT_FOUND));

        if (card.getFromUser().getUserId() != vo.getRequestUserId()) {
            throw new InvalidException("user_id", vo.getRequestUserId(), Error.UPDATE_FORBIDDEN);
        }

        if (vo.getContent() != null) {
            card.updateContent(vo.getContent());
        }
        if (vo.getSid() != null) {
            card.updateSid(vo.getSid());
        }

        cardRepository.save(card);
    }

    @Override
    public void delete(Long bno) throws Exception {

    }

    @Override
    public ListCardsResponse findAllReceivedCards(ListCardsRequest request) throws Exception {
        FindAllCardsParam param = FindAllCardsParam.builder()
                .groupIds(request.getGroupIds())
                .sids(request.getSids())
                .toUserId(request.getUserId())
                .lastId(request.getLastId())
                .size(request.getSize())
                .build();
        List<Cards> allCards = cardRepository.findAllCards(param);

        List<CardsDto> collect = allCards.stream()
                .map(m -> new CardsDto(m))
                .collect(Collectors.toList());

        Integer count = cardRepository.countBy(param).intValue();

        return ListCardsResponse.builder()
                .totalCount(count)
                .cards(collect)
                .build();
    }

    @Override
    public ListCardsResponse findAllSentCards(ListCardsRequest request) throws Exception {
        FindAllCardsParam param = FindAllCardsParam.builder()
                .groupIds(request.getGroupIds())
                .sids(request.getSids())
                .fromUserId(request.getUserId())
                .lastId(request.getLastId())
                .size(request.getSize())
                .build();
        List<Cards> allCards = cardRepository.findAllCards(param);

        List<CardsDto> collect = allCards.stream()
                .map(m -> new CardsDto(m))
                .collect(Collectors.toList());

        Integer count = cardRepository.countBy(param).intValue();

        return ListCardsResponse.builder()
                .totalCount(count)
                .cards(collect)
                .build();
    }

    @Override
    public List<CardsDto> findAllCardsByGroup(ListCardsByGroupRequest request) throws Exception {
        // TODO validate (해당 그룹원인지 확인)
        List<Cards> allCards = cardRepository.findAllCards(
                FindAllCardsParam.builder()
                        .groupIds(request.getGroupIds())
                        .sids(request.getSids())
                        .lastId(request.getLastId())
                        .size(request.getSize())
                        .build());

        List<CardsDto> collect = allCards.stream()
                .map(m -> new CardsDto(m))
                .collect(Collectors.toList());

        return collect;
    }
}
