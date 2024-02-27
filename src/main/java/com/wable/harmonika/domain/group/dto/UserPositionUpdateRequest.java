package com.wable.harmonika.domain.group.dto;

import com.wable.harmonika.domain.group.entity.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPositionUpdateRequest {
    @NotBlank
    private String userId;

    @NotNull
    private Position position;

    public UserPositionUpdateRequest(String userId, Position position) {
        this.userId = userId;
        this.position = position;
    }
}
