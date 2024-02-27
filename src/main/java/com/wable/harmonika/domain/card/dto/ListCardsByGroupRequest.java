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
    @Parameter(description = "조회하고 싶은 그룹 아이디(보통은 하나만 들어감)", required = true, example = "[1]")
    private List<Long> groupIds;
    @Parameter(description = "사용 안해도 됨", example = "[1]")
    private List<CardNames> sids;

    private String requestUserId;
    @PositiveOrZero
    private Long lastId;
    @Positive
    private Integer size;
}
