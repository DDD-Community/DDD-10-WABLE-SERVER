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
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Tag(name = "프로필 API", description = "프로필 API")
@Slf4j
@RestController
@RequestMapping("/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "프로필 조회", description = "프로필을 조회한다")
    public ResponseEntity<GetProfileResponseDto[]> getProfile(
            Users user,
            @RequestParam(value = "groupId", required = false) Long groupId,
            @RequestParam(value = "userId", required = false) String toUserId
    ) {
        String userId = user.getUserId();

        if (groupId != null) {
            // 그룹 내 프로필 전달
            profileService.validateProfileGroupByUserIdAndGroupId(userId, groupId);
            List<Profiles> userProfile = profileService.getProfileByGroupId(userId, groupId);
            GetProfileResponseDto[] response = userProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
            return ResponseEntity.ok(response);
        }

        if (toUserId != null) {
            // 유저 프로필 전달
            profileService.validateProfileByUserId(toUserId);
            List<Profiles> userProfile = profileService.getOtherProfileByUser(userId, toUserId);

            GetProfileResponseDto[] response = userProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
            return ResponseEntity.ok(response);
        }

        // 내 프로필 전달
        profileService.validateProfileByUserId(userId);
        List<Profiles> userGroupProfile = profileService.getProfileByUserId(userId);
        GetProfileResponseDto[] response = userGroupProfile.stream().map(GetProfileResponseDto::new).toArray(GetProfileResponseDto[]::new);
        return ResponseEntity.ok(response);
    }

    // 유저 프로필 생성
    @Operation(summary = "유저 프로필 등록", description = "유저 프로필을 작성한다")
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveProfileByUser(Users users, @Valid @RequestBody CreateProfileByUserDto profileByUserDto) {
        profileByUserDto.setUserId(users.getUserId());
        profileService.validateProfileByUser(profileByUserDto);
        profileService.saveProfileByUser(profileByUserDto);
    }

    // 그룹 프로필 생성
    @Operation(summary = "그룹 프로필 등록", description = "그룹 프로필을 작성한다")
    @PostMapping("/group")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveProfile(Users users, @Valid @RequestBody CreateProfileByGroupDto profileByGroupDto) {
        profileByGroupDto.setUserId(users.getUserId());
        profileService.validateProfileByGroup(profileByGroupDto);
        profileService.saveProfileByGroup(profileByGroupDto);
    }

    @Operation(summary = "프로필의 이미지 업로드 URL", description = "프로필의 이미지 업로드 URL 을 생성해서 준다 /profiles/{userId}.jpg or /profiles/{userId}/{groupId}.jpg")
    @GetMapping("/presigned-url")
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, String> makeImageUploadURL(
            Users users ,
            @RequestParam(value = "groupId", required = false) Long groupId
    ) {
        String userId = users.getUserId();
        if (groupId == null) {
            // 유저 프로필
            String filePath = "/profiles/" + userId + ".jpg";
            Map<String, String> response = profileService.getSignedUrl(filePath);

            return response;
        }

        profileService.validateGroupExistCheck(groupId);

        String fileName = "/profiles/" + userId + "/" + groupId + ".jpg";
        Map<String, String> response = profileService.getSignedUrl(fileName);
        return response;
    }

    @Operation(summary = "프로필 수정", description = "프로필을 수정한다")
    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProfile(
            Users users,
            @Valid @RequestBody UpdateProfileDto profileDto
    ) {
        profileDto.setUserId(users.getUserId());
        // Profile 을 수정할 때 중요한건... GroupId 가 Null 인지 아닌지만 알면 된다.
        if (profileDto.getGroupId() == null) {
            profileService.validateProfileByUserId(profileDto.getUserId());
            profileService.updateProfile(profileDto);
        }

        profileService.validateProfileGroupByUserIdAndGroupId(profileDto.getUserId(), profileDto.getGroupId());
        profileService.updateProfile(profileDto);
    }
}
