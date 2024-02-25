package com.wable.harmonika.domain.group.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GroupModifyRequest {

    private String name;

    private List<FixedQuestion> fixedQuestions;

    private List<String> requiredQuestions;

    @Getter
    public static class FixedQuestion {
        private Long id;
        private boolean required;
    }
}
