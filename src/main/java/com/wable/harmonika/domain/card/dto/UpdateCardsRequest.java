package com.wable.harmonika.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wable.harmonika.domain.card.entity.CardNames;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateCardsRequest {
    @NotBlank
    @Size(min = 5)
    private String content;

    @JsonProperty("isVisible")
    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    private Long toUserId;

    private Long groupId;
}
