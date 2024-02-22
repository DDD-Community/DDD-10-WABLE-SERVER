package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.user.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // 그룹 리스트
    @GetMapping
    public ResponseEntity<GroupListResponse> findAllGroup(Long userId, Users user) {
        GroupListResponse groupListResponse = groupService.findAll(userId);

        return ResponseEntity.ok(groupListResponse);
    }

    // 그룹 생일 리스트
    @GetMapping("/{groupId}/birthday")
    public ResponseEntity<GroupUserBirthdayListResponse> findAllBirthday(
            @PathVariable("groupId") Long groupId, Users user) {
        GroupUserBirthdayListResponse groupBirthdays = groupService.findAllBirthday(groupId);

        return ResponseEntity.ok(groupBirthdays);
    }

    // 팀원 목록
    @GetMapping("/{groupId}/members")
    public ResponseEntity<GroupMemberListResponse> findAllMember(
            @PathVariable("groupId") Long groupId, @RequestParam("name") String name, // todo 팀원 검색
            PagingMemberRequest request, Users user) {

        GroupMemberListResponse members = groupService.findAllMember(groupId, request.getLastName(),
                request.getSize());

        return ResponseEntity.ok(members);
    }

    // 팀원 역할 수정
    // todo 관리자 권한 체크
    @PutMapping("/{groupId}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable("groupId") Long groupId, @RequestBody UserRoleUpdateRequest request, Users user) {
        groupService.updateUserRole(groupId, request);

        return ResponseEntity.ok().build();
    }

    // 팀원 퇴출
    // todo 관리자 권한 체크
//    @PutMapping("/{groupId}/role")
//    public ResponseEntity<String> updateUserRole(@PathVariable("groupId") Long groupId) {
//        groupService.updateUserRole(groupId, request);
//
//        return ResponseEntity.ok().build();
//    }
}
