package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.group.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class ListCardsResponse {

    private Integer totalCount;
    private List<CardsDto> cards;
    @Builder
    public ListCardsResponse(Integer totalCount, List<CardsDto> cards) {
        this.totalCount = totalCount;
        this.cards = cards;
    }
}

