package com.wable.harmonika.domain.group.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UserResponse {
    private String userId;

    private String imageUrl;

    private String name;

    private String position;

    private LocalDate birthday;

    public UserResponse(String userId, String imageUrl, String name, String position,
            LocalDate birthday) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.position = position;
        this.birthday = birthday;
    }
}
