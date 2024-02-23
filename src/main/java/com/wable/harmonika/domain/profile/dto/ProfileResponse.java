package com.wable.harmonika.domain.profile.dto;

public class ProfileResponse {
    private GetUserProfileDto userProfileDto;
    private CreateProfileByGroupDto groupProfileDto;

    // 생성자, getter 및 setter 메서드 생략

    public ProfileResponse(GetUserProfileDto userProfileDto) {
        this.userProfileDto = userProfileDto;
    }

    public ProfileResponse(CreateProfileByGroupDto groupProfileDto) {
        this.groupProfileDto = groupProfileDto;
    }
}
