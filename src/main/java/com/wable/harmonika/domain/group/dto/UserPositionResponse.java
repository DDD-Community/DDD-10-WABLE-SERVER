package com.wable.harmonika.domain.group.dto;

import lombok.Getter;

@Getter
public class UserPositionResponse {
    private boolean isOwner;

    public UserPositionResponse(boolean isOwner) {
        this.isOwner = isOwner;
    }
}
