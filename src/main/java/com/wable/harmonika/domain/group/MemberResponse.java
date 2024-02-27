package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.user.entity.Users;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long userId;
    private String imageUrl;
    private String name;
    private String position ;
    private LocalDate birthday;

    public MemberResponse(Long userId, String imageUrl, String name, String position,
            LocalDate birthday) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.position = position;
        this.birthday = birthday;
    }
}
