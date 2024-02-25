package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
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
