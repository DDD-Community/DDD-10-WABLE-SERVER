package com.wable.harmonika.domain.profile.api;

import com.wable.harmonika.domain.profile.dto.CreateProfileByGroupDto;
import com.wable.harmonika.domain.profile.dto.CreateProfileByUserDto;
import com.wable.harmonika.domain.profile.dto.ProfileResponseDto;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Tag(name = "프로필 API", description = "프로필 API")
@Slf4j
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "프로필 조회", description = "프로필을 조회한다")
    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileResponseDto getProfile(
            @RequestParam(value = "groupId", required = false) Long groupId,
            @RequestParam(value = "userId", required = false) String toUserId
    ) {
        String userId = "1234";

        if (groupId != null) {
            // 그룹 내 프로필 전달
            profileService.validateProfileGroupByUserIdAndGroupId(userId, groupId);
            Profiles userProfile = profileService.getProfileByGroupId(groupId);
            return new ProfileResponseDto(userProfile);
        }

        if (toUserId != null) {
            // 유저 프로필 전달
            profileService.validateProfileByUserId(toUserId);
            Profiles userProfile = profileService.getProfileByUserId(toUserId);
            return new ProfileResponseDto(userProfile);
        }

        // 내 프로필 전달
        profileService.validateProfileByUserId(userId);
        Profiles userGroupProfile = profileService.getProfileByUserId(userId);
        return new ProfileResponseDto(userGroupProfile);
    }

    // 유저 프로필 생성
    @Operation(summary = "유저 프로필 등록", description = "유저 프로필을 작성한다")
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveProfileByUser(@Valid @RequestBody CreateProfileByUserDto profileByUserDto) {
        profileService.validateProfileByUser(profileByUserDto);
        profileService.saveProfileByUser(profileByUserDto);
    }


    // 그룹 프로필 생성
    @Operation(summary = "그룹 프로필 등록", description = "그룹 프로필을 작성한다")
    @PostMapping("/group")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveProfile(@Valid @RequestBody CreateProfileByGroupDto profileByGroupDto) {
        profileService.validateProfileByGroup(profileByGroupDto);
        profileService.saveProfileByGroup(profileByGroupDto);
    }

    @Operation(summary = "프로필의 이미지 업로드 URL", description = "프로필의 이미지 업로드 URL 을 생성해서 준다 /profiles/{userId}.jpg or /profiles/{userId}/{groupId}.jpg")
    @GetMapping("/presigned-url")
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, String> makeImageUploadURL(@RequestParam(value = "groupId", required = false) String groupId) {
        String userId = "1234";
        if (groupId == null) {
            // 유저 프로필
            String filePath = "/profiles/" + userId + ".jpg";
            Map<String, String> response = profileService.getSignedUrl(filePath);

            return response;
        }

        String fileName = "/profiles/" + userId + "/" + groupId + ".jpg";
        Map<String, String> response = profileService.getSignedUrl(fileName);
        return response;
    }

//    // profile update
//    @Operation(summary = "프로필 수정", description = "프로필을 수정한다")
//    @PutMapping()
//    @ResponseStatus(value = HttpStatus.OK)
//    public String updateProfile(@RequestParam(value = "group_id", required = false) Long groupId, @RequestBody GroupProfileDto profileDto) {
//        // group_id 가 있는 경우 profile, profile_question 에 수정
//        // group_id 가 없는 경우 user 테이블에 수정
//
//        return "UpdateProfileController";
//    }
}
