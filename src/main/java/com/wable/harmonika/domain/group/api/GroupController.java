package com.wable.harmonika.domain.group.api;

import com.wable.harmonika.domain.group.dto.GroupDetailResponse;
import com.wable.harmonika.domain.group.dto.GroupListResponse;
import com.wable.harmonika.domain.group.dto.GroupModifyRequest;
import com.wable.harmonika.domain.group.dto.GroupUserBirthdayListResponse;
import com.wable.harmonika.domain.group.dto.GroupUserListResponse;
import com.wable.harmonika.domain.group.dto.UserPositionResponse;
import com.wable.harmonika.domain.group.dto.UserPositionUpdateRequest;
import com.wable.harmonika.domain.group.service.GroupService;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.auth.TokenGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Tag(name = "그룹 API", description = "그룹 API")
@Slf4j
@RestController
@RequestMapping("/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private final TokenGenerator tokenGenerator;

    @Operation(summary = "내가 속한 그룹 리스트", description = "내가 속한 그룹 리스트")
    @GetMapping()
    public ResponseEntity<GroupListResponse> findAllGroup(@Parameter(hidden = true) Users user) {
        GroupListResponse groupListResponse = groupService.findAll(user);

        return ResponseEntity.ok(groupListResponse);
    }

    @Operation(summary = "유저의 어드민 유무", description = "유저의 어드민 유무")
    @GetMapping("/{groupId}/user/position")
    public ResponseEntity<UserPositionResponse> findUserPosition(
            @Parameter(hidden = true) Users user,
            @PathVariable("groupId") Long groupId) {

        UserPositionResponse userPosition = groupService.getUserPosition(user, groupId);

        return ResponseEntity.ok(userPosition);
    }

    @Operation(summary = "그룹내 생일자 리스트", description = "그룹내 생일자 리스트")
    @GetMapping("/{groupId}/birthday")
    public ResponseEntity<GroupUserBirthdayListResponse> findAllBirthday(
            @Parameter(hidden = true) Users user,
            @PathVariable("groupId") Long groupId) {
        GroupUserBirthdayListResponse groupBirthdays = groupService.findAllBirthday(groupId);

        return ResponseEntity.ok(groupBirthdays);
    }

    @Operation(summary = "그룹내 팁원 리스트", description = "그룹내 팁원 리스트")
    @GetMapping("/{groupId}/users")
    public ResponseEntity<GroupUserListResponse> findAllMember(
            @Parameter(hidden = true) Users user,
            @PathVariable("groupId") Long groupId,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        GroupUserListResponse members = groupService.findAllMember(groupId, lastName, searchName,
                size, user);

        return ResponseEntity.ok(members);
    }

    @Operation(summary = "그룹 생성", description = "그룹 생성")
    @PostMapping("")
    public ResponseEntity<String> createGroup(
            @Parameter(hidden = true) Users user,
            @RequestBody GroupModifyRequest request) {

        groupService.validatorGroupName(request.getName());
        groupService.createGroup(user, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "그룹 수정", description = "그룹 수정")
    @PutMapping("/{groupId}")
    public ResponseEntity<String> updateGroup(
            @Parameter(hidden = true) Users user,
            @PathVariable Long groupId,
            @Valid @RequestBody GroupModifyRequest request) {

        groupService.validatorGroupOwner(user, groupId);
        groupService.updateGroup(request, groupId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "그룹 상세 조회", description = "그룹 상세 조회")
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailResponse> findGroup(
            @Parameter(hidden = true) Users user,
            @PathVariable Long groupId) {
        groupService.validatorGroupOwner(user, groupId);
        GroupDetailResponse response = groupService.findGroup(groupId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "팀원 역할 수정", description = "팀원 역할 수정")
    @PutMapping("/{groupId}/role")
    public ResponseEntity<String> updateUserRole(
            @Parameter(hidden = true) Users user,
            @PathVariable("groupId") Long groupId,
            @Valid @RequestBody UserPositionUpdateRequest request
    ) {
        groupService.validatorGroupOwner(user, groupId);
        groupService.updateUserRole(groupId, request, user);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "초대코드 토큰 생성", description = "초대 코드 토큰생성")
    @GetMapping("/{groupId}/invitationCode")
    public ResponseEntity<Map<String, String>> updateUserRole(
            Users user,
            @PathVariable("groupId") Long groupId
    ) {
        groupService.validatorGroupOwner(user, groupId);

        String token = tokenGenerator.generateJwtToken();

        Map<String, String> responseData = new HashMap<>();
        responseData.put("data", token);

        return ResponseEntity.ok(responseData);
    }
}
