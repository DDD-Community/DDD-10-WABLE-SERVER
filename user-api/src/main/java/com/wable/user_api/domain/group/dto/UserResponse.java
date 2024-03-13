package com.wable.user_api.domain.group.dto;

import java.time.LocalDate;

import com.wable.user_api.domain.group.entity.Position;
import lombok.Getter;

@Getter
public class UserResponse {
    private String userId;

    private String imageUrl;

    private String name;

    private Position position;

    private LocalDate birthday;

    public UserResponse(String userId, String imageUrl, String name, Position position,
            LocalDate birthday) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.position = position;
        this.birthday = birthday;
    }
}
