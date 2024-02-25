package com.wable.harmonika.domain.group.dto;

import com.wable.harmonika.global.entity.Role;
import lombok.Getter;

@Getter
public class UserRoleUpdateRequest {
    private Long userId;

    private Role role;
}
