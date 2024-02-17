package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;

public class GroupProfileDto {

    private Long id;
    private Long user_id;
    private Long group_id;
    private Users users;
    private Groups groups;
    private String nickname;
    private String profile_image_url;

    public GroupProfileDto(Profiles profile) {
        this.id = profile.getId();
        this.users = profile.getUser();
        this.groups = profile.getGroup();
        this.nickname = profile.getNickname();
        this.profile_image_url = profile.getProfileImageUrl();
    }
}
