package com.wable.user_api.domain.group.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GroupUserListResponse {

    private Integer totalCount;
    private boolean isOwner;
    private List<UserResponse> users;

    public GroupUserListResponse(Integer totalCount, boolean isOwner, List<UserResponse> users) {
        this.totalCount = totalCount;
        this.isOwner = isOwner;
        this.users = users;
    }
}
