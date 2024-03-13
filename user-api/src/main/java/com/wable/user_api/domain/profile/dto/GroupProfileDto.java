package com.wable.user_api.domain.profile.dto;

import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.user.entity.Users;
import lombok.Getter;

@Getter
public class GroupProfileDto {

    private Long id;
    private String userId;
    private Long groupId;
    private Users users;
    private Groups groups;
    private String nickname;
    private String profileImageUrl;

    public GroupProfileDto(Profiles profile) {
        this.id = profile.getId();
        this.users = profile.getUser();
        this.groups = profile.getGroup();
        this.nickname = profile.getNickname();
        this.profileImageUrl = profile.getProfileImageUrl();
    }
}
