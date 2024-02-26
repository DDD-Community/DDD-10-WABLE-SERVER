package com.wable.harmonika.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wable.harmonika.domain.card.entity.CardNames;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardsRequest {
    private Long cardId;
    @NotBlank
    @Size(min = 5)
    private String content;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    private String requestUserId;
}
