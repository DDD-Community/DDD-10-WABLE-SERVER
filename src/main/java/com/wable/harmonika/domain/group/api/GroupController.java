package com.wable.harmonika.domain.group.api;

import com.wable.harmonika.domain.group.dto.*;
import com.wable.harmonika.domain.group.service.GroupService;
import com.wable.harmonika.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "내가 속한 그룹 리스트", description = "내가 속한 그룹 리스트")
    @GetMapping()
    public ResponseEntity<GroupListResponse> findAllGroup(Users user) {
        GroupListResponse groupListResponse = groupService.findAll(user);

        return ResponseEntity.ok(groupListResponse);
    }

    @Operation(summary = "그룹내 생일자 리스트", description = "그룹내 생일자 리스트")
    @GetMapping("/{groupId}/birthday")
    public ResponseEntity<GroupUserBirthdayListResponse> findAllBirthday(
            Users user,
            @PathVariable("groupId") Long groupId) {
        GroupUserBirthdayListResponse groupBirthdays = groupService.findAllBirthday(groupId);

        return ResponseEntity.ok(groupBirthdays);
    }

    @Operation(summary = "그룹내 팁원 리스트", description = "그룹내 팁원 리스트")
    @GetMapping("/{groupId}/users")
    public ResponseEntity<GroupUserListResponse> findAllMember(
            Users user,
            @PathVariable("groupId") Long groupId,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        GroupUserListResponse members = groupService.findAllMember(groupId, lastName, searchName, size);

        return ResponseEntity.ok(members);
    }

    @Operation(summary = "그룹 생성", description = "그룹 생성")
    @PostMapping("")
    public ResponseEntity<String> createGroup(
            Users user,
            @RequestBody GroupModifyRequest request) {

        groupService.createGroup(user, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "그룹 수정", description = "그룹 수정")
    @PutMapping("/{groupId}")
    public ResponseEntity<String> updateGroup(
            Users user,
            @PathVariable Long groupId,
            @RequestBody GroupModifyRequest request) {

        groupService.validatorGroup(user, groupId);
        groupService.updateGroup(request, groupId);

        return ResponseEntity.ok().build();
    }

    // TODO: 관리자 권한 체크
    @Operation(summary = "팀원 역할 수정", description = "팀원 역할 수정 + todo 관리자 권한 체크 해야함")
    @PutMapping("/{groupId}/role")
    public ResponseEntity<String> updateUserRole(
            Users user,
            @PathVariable("groupId") Long groupId, @RequestBody UserRoleUpdateRequest request) {
        groupService.updateUserRole(groupId, request, user);

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
