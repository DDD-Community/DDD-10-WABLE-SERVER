package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.entity.Groups;
import java.util.List;
import java.util.stream.Collectors;

public class GroupListResponse {
    private List<GroupResponse> groupResponses;

    public GroupListResponse(List<GroupResponse> groupResponses) {
        this.groupResponses = groupResponses;
    }

    public static GroupListResponse of(List<Groups> groups) {
        List<GroupResponse> groupResponses = groups.stream()
                .map(group -> new GroupResponse(group.getId(), group.getName()))
                .collect(Collectors.toList());

        return new GroupListResponse(groupResponses);
    }

    static class GroupResponse {
        private Long id;
        private String name;

        public GroupResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
