package com.wable.user_api.domain.group.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GroupModifyRequest {

    @NotBlank
    private String name;

    @Getter
    public static class FixedQuestion {
        private Long id;
    }
}
