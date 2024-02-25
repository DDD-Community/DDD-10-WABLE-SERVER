package com.wable.harmonika.domain.group.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GroupUserListResponse {

    private Integer totalCount;
    private List<UserResponse> users;

    public GroupUserListResponse(Integer totalCount, List<UserResponse> users) {
        this.totalCount = totalCount;
        this.users = users;
    }
}
