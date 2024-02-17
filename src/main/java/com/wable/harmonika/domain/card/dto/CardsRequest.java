package com.wable.harmonika.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
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

    @JsonProperty("isVisible")
    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    private Long toUserId;

    private Long groupId;
}
