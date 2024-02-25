package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.card.entity.Cards;
import lombok.Getter;

@Getter
public class CardsDto {
    private Long id;

    private String content;

    private String fromUserId;

    private    String fromUserNickname;

    private String fromUserProfileImageUrl;

    private String toUserId;

    private String toUserNickname;

    private String toUserProfileImageUrl;

    public CardsDto(Cards cards) {
        this.id = cards.getId();
        this.content = cards.getContent();
        this.fromUserId = cards.getFromUser().getUserId();
        this.toUserId = cards.getToUser().getUserId();
        this.fromUserNickname = cards.getFromUserProfile().getNickname();
        this.toUserNickname = cards.getToUserProfile().getNickname();
        this.fromUserProfileImageUrl = cards.getFromUserProfile().getProfileImageUrl();
        this.toUserProfileImageUrl = cards.getToUserProfile().getProfileImageUrl();

    }
}
