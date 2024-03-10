package com.wable.harmonika.domain.group.dto;

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
