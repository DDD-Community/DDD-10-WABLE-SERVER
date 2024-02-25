package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.profile.dto.GroupProfileDto;
import com.wable.harmonika.domain.user.entity.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Map;

public class CardsResponse {
    private Long id;

    private String content;

    private String fromUserId;

    private String fromUserNickname;

    private String fromUserProfileImageUrl;

    private String toUserId;

    private String toUserNickname;

    private String toUserProfileImageUrl;

    public CardsResponse(CardsDto cardsDto, Map<String, GroupProfileDto> groupProfileMap) {
        this.id = cardsDto.getId();
        this.content = cardsDto.getContent();
        this.fromUserId = cardsDto.getFromUserId();
        this.toUserId = cardsDto.getToUserId();
        this.fromUserNickname = groupProfileMap.get(cardsDto.getFromUserId()).getNickname();
        this.toUserNickname = groupProfileMap.get(cardsDto.getToUserId()).getNickname();
        this.fromUserProfileImageUrl = groupProfileMap.get(cardsDto.getFromUserId()).getProfileImageUrl();
        this.toUserProfileImageUrl = groupProfileMap.get(cardsDto.getToUserId()).getProfileImageUrl();
    }
}