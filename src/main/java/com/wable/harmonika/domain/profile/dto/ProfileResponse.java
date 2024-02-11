package com.wable.harmonika.domain.profile.dto;

public class ProfileResponse {
    private UserProfileDto userProfileDto;
    private GroupProfileDto groupProfileDto;

    // 생성자, getter 및 setter 메서드 생략

    public ProfileResponse(UserProfileDto userProfileDto) {
        this.userProfileDto = userProfileDto;
    }

    public ProfileResponse(GroupProfileDto groupProfileDto) {
        this.groupProfileDto = groupProfileDto;
    }
}
