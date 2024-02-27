package com.wable.harmonika.domain.card.dto;

import com.wable.harmonika.domain.card.entity.CardNames;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ListCardsByGroupRequest {
    @NotEmpty
    private List<Long> groupIds;
    private List<CardNames> sids;

    private String requestUserId;
    @PositiveOrZero
    private Long lastId;
    @Positive
    private Integer size;
}
