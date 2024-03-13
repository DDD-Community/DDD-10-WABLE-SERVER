package com.wable.user_api.domain.card.dto;

import com.wable.user_api.domain.card.entity.CardNames;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Getter @Setter
public class ListCardsRequest {
    private List<Long> groupIds;

    private List<CardNames> sids;

    private String UserId;
    @PositiveOrZero
    private Long lastId;
    @Positive
    private Integer size;
}
