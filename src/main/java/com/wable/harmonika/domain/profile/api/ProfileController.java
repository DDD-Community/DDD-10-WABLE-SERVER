package com.wable.harmonika.domain.profile.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wable.harmonika.domain.profile.dto.CreateProfileByGroupDto;
import com.wable.harmonika.domain.profile.dto.CreateProfileByUserDto;
import com.wable.harmonika.domain.profile.dto.GetProfileResponseDto;
import com.wable.harmonika.domain.profile.dto.UpdateProfileDto;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.service.ProfileService;
import com.wable.harmonika.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

import java.util.List;
import java.util.Map;


@Tag(name = "프로필 API", description = "프로필 API")
@Slf4j
@RestController
@RequestMapping("/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "내 프로필 조회", description = "내 프로필을 조회한다")
    @GetMapping("/me")
    public ResponseEntity<GetProfileResponseDto[]> getProfileMe(@Parameter(hidden = true) Users user) {
        String userId = user.getUserId();
        profileService.validateProfileByUserId(userId);
        List<Profiles> userGroupProfile = profileService.getProfileByUserId(userId);
        GetProfileResponseDto[] response = userGroupProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "그룹 프로필 조회", description = "그룹 프로필 조회")
    @Parameters({
            @Parameter(name = "groupId", in = ParameterIn.PATH, description = "그룹 아이디 리스트", schema = @Schema(type = "string"), required = true),
            @Parameter(name = "userId", in = ParameterIn.QUERY, description = "유저 아이디", schema = @Schema(type = "string"), required = false)
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<GetProfileResponseDto[]> getProfile(
            @Parameter(hidden = true) Users user,
            @PathVariable Long groupId,
            @RequestParam(value = "userId", required = false) String targetUserId
    ) {
        String userId = user.getUserId();

        if (targetUserId != null) {
            // 유저 프로필 전달
            profileService.validateProfileGroupByUserIdAndGroupId(targetUserId, groupId);
            profileService.validateProfileGroupByUserIdAndGroupId(userId, groupId);
            List<Profiles> userProfile = profileService.getOtherProfileByUser(userId, targetUserId);

            GetProfileResponseDto[] response = userProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
            return ResponseEntity.ok(response);
        }

        // 그룹 내 프로필 전달
        profileService.validateProfileGroupByUserIdAndGroupId(userId, groupId);
        List<Profiles> userProfile = profileService.getProfileByGroupId(userId, groupId);
        GetProfileResponseDto[] response = userProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
        return ResponseEntity.ok(response);
    }

    // 유저 프로필 생성
    @Operation(summary = "유저 프로필 등록", description = "유저 프로필을 작성한다")
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> saveProfileByUser(
            @Parameter(hidden = true) Users users,
            @Valid @RequestBody CreateProfileByUserDto profileByUserDto)
    {
        profileByUserDto.setUserId(users.getUserId());
        profileService.validateProfileByUser(profileByUserDto);
        profileService.saveProfileByUser(profileByUserDto);

        Map<String, String> responseData = Collections.singletonMap("data", "success");
        return ResponseEntity.ok(responseData);
    }

    // 그룹 프로필 생성
    @Operation(summary = "그룹 프로필 등록", description = "그룹 프로필을 작성한다")
    @PostMapping("/group")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> saveProfile(
            @Parameter(hidden = true) Users users,
            @Valid @RequestBody CreateProfileByGroupDto profileByGroupDto)
    {
        profileByGroupDto.setUserId(users.getUserId());
        profileService.validateProfileByGroup(profileByGroupDto);
        profileService.validateGroupToken(profileByGroupDto.getToken());
        profileService.saveProfileByGroup(profileByGroupDto);

        Map<String, String> responseData = Collections.singletonMap("data", "success");
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "프로필의 이미지 업로드 URL", description = "프로필의 이미지 업로드 URL 을 생성해서 준다 /profiles/{userId}.jpg or /profiles/{userId}/{groupId}.jpg")
    @GetMapping("/presigned-url")
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, String> makeImageUploadURL(
            @Parameter(hidden = true) Users users ,
            @RequestParam(value = "groupId", required = false) Long groupId
    ) {
        String userId = users.getUserId();
        if (groupId == null) {
            // 유저 프로필
            String filePath = "profile/" + userId + ".jpg";
            Map<String, String> response = profileService.getSignedUrl(filePath);

            return response;
        }

        profileService.validateGroupExistCheck(groupId);

        String fileName = "profile/" + userId + "/" + groupId + ".jpg";
        Map<String, String> response = profileService.getSignedUrl(fileName);
        return response;
    }

    @Operation(summary = "유저의 기본 프로필 수정", description = "프로필을 수정한다.")
    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> updateProfileByUser(
            @Parameter(hidden = true) Users users,
            @Valid @RequestBody UpdateProfileDto profileDto
    ) {
        profileDto.setUserId(users.getUserId());

        profileService.validateProfileByUserId(profileDto.getUserId());
        profileService.updateProfile(profileDto);

        Map<String, String> responseData = Collections.singletonMap("data", "success");
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "유저의 그룹 프로필 수정", description = "유저의 프로필을 수정한다. PATH 에 그룹 아이디를 넣어줘야 한다.")
    @Parameters({
            @Parameter(name = "groupId", in = ParameterIn.PATH, description = "그룹 아이디 리스트", schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/group/{groupId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> updateProfileGroup(
            @Parameter(hidden = true) Users users,
            @PathVariable Long groupId,
            @Valid @RequestBody UpdateProfileDto profileDto
    ) {
        profileDto.setUserId(users.getUserId());
        profileDto.setGroupId(groupId);

        profileService.validateProfileGroupByUserIdAndGroupId(profileDto.getUserId(), profileDto.getGroupId());
        profileService.updateProfile(profileDto);

        Map<String, String> responseData = Collections.singletonMap("data", "success");
        return ResponseEntity.ok(responseData);
    }
}
