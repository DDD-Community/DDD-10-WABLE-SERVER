package com.wable.harmonika.domain.group.dto;

import com.wable.harmonika.global.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateRequest {
    @NotBlank
    private String userId;

    @NotNull
    private Role role;

    public UserRoleUpdateRequest(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }
}
