package com.wable.harmonika.domain.group.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GroupModifyRequest {

    @NotBlank
    private String name;

    private List<FixedQuestion> fixedQuestions = List.of();

    private List<String> requiredQuestions = List.of();

    @Getter
    public static class FixedQuestion {
        private Long id;
        private boolean required;
    }
}
