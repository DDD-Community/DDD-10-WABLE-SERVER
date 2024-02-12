package com.wable.harmonika.domain.profile.api;

import com.wable.harmonika.domain.profile.dto.GroupProfileDto;
import com.wable.harmonika.domain.profile.dto.ProfileResponse;
import com.wable.harmonika.domain.profile.dto.UserProfileDto;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.service.ProfileService;
import com.wable.harmonika.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "프로필 API", description = "프로필 API")
@Slf4j
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService ProfileService;

    @Operation(summary = "프로필 조회", description = "프로필을 조회한다")
    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ProfileResponse getProfile(@RequestParam(value = "group_id", required = false) Long groupId, @RequestParam(value = "user_id", required = false) Long toUserId) {
        // 1. group_id 가 있는 경우 profile, profile_question 에서 가져 오기
        // 1. group_id 가 없는 경우 user 테이블에서 가져 오기

        // 2.1 유저 ID 으로 그룹 있는지
        if (groupId == null) {
            Users userProfile = ProfileService.getUserProfileByUserId(1L);
            return new ProfileResponse(new UserProfileDto(userProfile));
        }

        if (toUserId != null) {
            // 토큰 유저와 toUserId 가 같은 소속인지 확인
            // toUserId 의 profile, profile_question 가져 오기
            return new ProfileResponse(new GroupProfileDto(ProfileService.getProfile(toUserId)));
        }

        // 2. profile, profile_question 가져 오기
        Profiles userGroupProfile = ProfileService.getProfile(1L);
        return new ProfileResponse(new GroupProfileDto(userGroupProfile));
    }

    @Operation(summary = "프로필 등록", description = "프로필을 작성한다")
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveProfile(@RequestParam(value = "group_id", required = false) Long groupId, @RequestBody GroupProfileDto profileDto) {
        // group_id 가 있는 경우 profile, profile_question 에 생성
        // group_id 가 없는 경우 user 테이블에 생성

        return "InsertProfileController";
    }

    @Operation(summary = "프로필의 이미지 업로드 URL", description = "프로필의 이미지 업로드 URL 을 생성해서 준다")
    @GetMapping("/presigned-url")
    @ResponseStatus(value = HttpStatus.OK)
    public String makeImageUploadURL(@RequestParam(value = "group_id", required = false) Long groupId) {
        // 1. 유저 정보 확인 (유저 토큰 가져온 후 UserId 을 가져 와야 함)
        // 1.1. 유저 정보가 없으면 에러 --> 어노테이션으로 처리

        // 2 그룹 파라메터가 있는지 확인 (쿼리 파라메터로 옵셔널하게 group_id 을 받아야 함)
        // 2.1 그룹 정보가 없으면 에러

        // 1. 서명된 URL 생성
        val response = ProfileService.getSignedUrl(activeProfile, imageBucketName, imageBucketPath, fileName);

        // 2. 서명된 URL 반환

        // 3. 이미지 업로드 가능한 URL 생성
        // 3.1 주면 됨
        return ResponseEntity.ok(new SignedUrlDto.success("데이터", response));
    }

    // profile update
    @Operation(summary = "프로필 수정", description = "프로필을 수정한다")
    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public String updateProfile(@RequestParam(value = "group_id", required = false) Long groupId, @RequestBody GroupProfileDto profileDto) {
        // group_id 가 있는 경우 profile, profile_question 에 수정
        // group_id 가 없는 경우 user 테이블에 수정

        return "UpdateProfileController";
    }
}
