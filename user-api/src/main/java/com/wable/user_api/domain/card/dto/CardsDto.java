package com.wable.user_api.domain.card.dto;

import com.wable.user_api.domain.card.entity.CardNames;
import com.wable.user_api.domain.card.entity.Cards;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardsDto {
    private Long id;

    private String content;

    private CardNames sid;

    private String fromUserId;

    private String fromUserNickname;

    private String fromUserProfileImageUrl;

    private String toUserId;

    private String toUserNickname;

    private String toUserProfileImageUrl;

    private LocalDateTime createdAt;

    public CardsDto(Cards cards) {
        this.id = cards.getId();
        this.content = cards.getContent();
        this.sid = cards.getSid();
        this.fromUserId = cards.getFromUser().getUserId();
        this.toUserId = cards.getToUser().getUserId();
        this.fromUserNickname = cards.getFromUserProfile().getNickname();
        this.toUserNickname = cards.getToUserProfile().getNickname();
        this.fromUserProfileImageUrl = cards.getFromUserProfile().getProfileImageUrl();
        this.toUserProfileImageUrl = cards.getToUserProfile().getProfileImageUrl();
        this.createdAt = cards.getToUserProfile().getCreatedAt();
    }
}
