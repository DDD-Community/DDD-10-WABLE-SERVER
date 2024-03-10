package com.wable.user_api.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wable.user_api.domain.card.entity.CardNames;
import com.wable.user_api.domain.group.entity.Groups;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CardsRequest {
    @NotBlank
    @Size(min = 5)
    private String content;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    private String toUserId;

    private String fromUserId;

    private Long groupId;
}
