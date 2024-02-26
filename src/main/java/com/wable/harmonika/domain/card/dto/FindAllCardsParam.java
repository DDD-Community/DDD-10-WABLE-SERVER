package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.card.entity.CardNames;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class FindAllCardsParam {
    private List<Long> groupIds;

    private List<CardNames> sids;

    private String toUserId;

    private String fromUserId;

    private Long lastId;

    private Integer size;

    @Builder
    public FindAllCardsParam(List<Long> groupIds, List<CardNames> sids, String toUserId, String fromUserId, Long lastId, Integer size) {
        this.groupIds = groupIds;
        this.sids = sids;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.lastId = lastId;
        this.size = size;
    }
}
