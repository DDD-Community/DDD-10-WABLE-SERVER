package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.user.entity.Users;
import java.util.List;
import lombok.Getter;

@Getter
public class GroupMemberListResponse {

    private Long count;
    private List<MemberResponse> memberResponses;

    public GroupMemberListResponse(Long count, List<MemberResponse> memberResponses) {
        this.count = count;
        this.memberResponses = memberResponses;
    }
}
