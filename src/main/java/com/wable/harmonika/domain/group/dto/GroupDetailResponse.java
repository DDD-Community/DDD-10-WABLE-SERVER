package com.wable.harmonika.domain.group.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GroupDetailResponse {

    private Long id;
    private String name;
    private List<QuestionsResponse> questions;

    public GroupDetailResponse(Long id, String name, List<QuestionsResponse> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }
}
