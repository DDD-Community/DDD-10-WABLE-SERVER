package com.wable.harmonika.domain.group;

import com.wable.harmonika.global.entity.Role;
import lombok.Getter;

@Getter
public class UserRoleUpdateRequest {

    private Long userId;
    private Role role;

}
