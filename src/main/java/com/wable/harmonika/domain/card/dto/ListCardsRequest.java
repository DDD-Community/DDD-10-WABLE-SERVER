package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.card.entity.CardNames;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Getter @Setter
public class ListCardsRequest {
    private List<Long> groupIds;

    private List<CardNames> sids;

    private Long lastId;

    private Integer size;
}
