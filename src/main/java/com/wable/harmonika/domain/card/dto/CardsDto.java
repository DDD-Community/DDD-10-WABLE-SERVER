package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.card.entity.Cards;
import lombok.Getter;

@Getter
public class CardsDto {
    private Long id;

    private String content;

    private String fromUserId;

    private String toUserId;

    public CardsDto(Cards cards) {
        this.id = cards.getId();
        this.content = cards.getContent();
        this.fromUserId = cards.getFromUser().getUserId();
        this.toUserId = cards.getToUser().getUserId();
    }
}
