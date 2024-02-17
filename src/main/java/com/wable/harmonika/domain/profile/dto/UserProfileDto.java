package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.user.entity.Users;

public class UserProfileDto {
    private Long id;

    private String nickname;

    private String profile_image_url;

    // TODO: 유저 프로필 부분 테이블 만들어야 할듯
    public UserProfileDto(Users userProfile) {
        this.id = userProfile.getId();
    }
}
