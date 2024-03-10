package com.wable.user_api.domain.profile.service;

import com.wable.user_api.domain.group.entity.Position;
import com.wable.user_api.domain.group.entity.UserGroups;
import com.wable.user_api.domain.group.repository.GroupRepository;
import com.wable.user_api.domain.group.repository.UserGroupRepository;
import com.wable.user_api.domain.profile.dto.CreateProfileByGroupDto;
import com.wable.user_api.domain.profile.dto.CreateProfileByUserDto;
import com.wable.user_api.domain.profile.dto.QuestionDataDto;
import com.wable.user_api.domain.profile.dto.UpdateProfileDto;
import com.wable.user_api.domain.profile.entity.ProfileQuestions;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.profile.repository.ProfileQuestionsRepository;
import com.wable.user_api.domain.profile.repository.ProfileRepository;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.domain.user.repository.UserRepository;
import com.wable.user_api.global.auth.TokenGenerator;
import com.wable.user_api.global.error.Error;
import com.wable.user_api.global.error.exception.InvalidException;
import com.wable.user_api.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileQuestionsRepository profileQuestionsRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final S3Presigner s3Presigner;
    private final TokenGenerator tokenGenerator;

    @Value("${cloud.aws.bucket}")
    private String imageBucketName;

    // NOTE: User, Profile 테이블에 데이터 있으면 이미 가입된 유저
    public void validateProfileByUser(CreateProfileByUserDto profileByUserDto) {
        boolean isRegisterUser = userRepository.existsByUserId(profileByUserDto.getUserId());
        if (isRegisterUser) {
            throw new InvalidException("userId", profileByUserDto.getUserId(), Error.PROFILE_DUPLICATION);
        }

        boolean hasGroup = profileRepository.existsByUserIdAndGroupIdIsNull(profileByUserDto.getUserId());
        if (hasGroup) {
            throw new InvalidException("userId", profileByUserDto.getUserId(), Error.PROFILE_DUPLICATION);
        }
    }
    
    public void  validateGroupExistCheck(Long groupId) {
        boolean existsGroup = groupRepository.existsById(groupId);
        if (existsGroup == false) {
            throw new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND);
        }
    }

    public void validateProfileByGroup(CreateProfileByGroupDto profileByGroupDto) {
        boolean isRegisterUser = userRepository.existsByUserId(profileByGroupDto.getUserId());
        if (isRegisterUser == false) {
            throw new InvalidException("userId", profileByGroupDto.getUserId(), Error.ACCOUNT_NOT_FOUND);
        }

        boolean existsGroup = groupRepository.existsById(profileByGroupDto.getGroupId());
        if (existsGroup == false) {
            throw new InvalidException("groupId", profileByGroupDto.getGroupId(), Error.GROUP_NOT_FOUND);
        }

        boolean hasGroup = profileRepository.existsByUserIdAndGroupId(profileByGroupDto.getUserId(), profileByGroupDto.getGroupId());
        if (hasGroup) {
            throw new InvalidException("groupId", profileByGroupDto.getUserId(), Error.GROUP_DUPLICATION);
        }
    }

    public void validateGroupToken(String groupToken) {
        boolean isValidToken = tokenGenerator.validateJwtToken(groupToken);
        if (isValidToken == false) {
            throw new UnauthorizedException("expired group token");
        }
    }

    private Users getUserBuilder(CreateProfileByUserDto profileByUserDto) {
        return Users.builder()
                .userId(profileByUserDto.getUserId())
                .name(profileByUserDto.getName())
                .gender(profileByUserDto.getGender())
                .birth(profileByUserDto.getBirth())
                .build();
    }

    private Profiles getProfileBuilder(CreateProfileByUserDto profileByUserDto) {
        return Profiles.builder()
                .nickname(profileByUserDto.getNickname())
                .user(userRepository.findByUserId(profileByUserDto.getUserId())
                        .orElseThrow(() -> new InvalidException("userId", profileByUserDto.getUserId(), Error.ACCOUNT_NOT_FOUND)))
                .profileImageUrl(profileByUserDto.getProfileImageUrl())
                .build();
    }

    private Profiles getProfileBuilder(CreateProfileByGroupDto profileByUserDto) {
        return Profiles.builder()
                .user(userRepository.findByUserId(profileByUserDto.getUserId())
                        .orElseThrow(() -> new InvalidException("userId", profileByUserDto.getUserId(), Error.ACCOUNT_NOT_FOUND)))
                .group(groupRepository.findById(profileByUserDto.getGroupId())
                        .orElseThrow(() -> new InvalidException("groupId", profileByUserDto.getGroupId(), Error.GROUP_NOT_FOUND)))
                .nickname(profileByUserDto.getNickname())
                .profileImageUrl(profileByUserDto.getProfileImageUrl())
                .build();
    }

    private List<ProfileQuestions> getProfileQuestionsBuilder(CreateProfileByUserDto profileByUserDto, Profiles profile) {
        return profileByUserDto.getQuestions().stream()
                .map(question -> ProfileQuestions.builder()
                        .profile(profile)
                        .sid(question.getSid())
                        .question(question.getQuestion())
                        .questionType(question.getQuestionType())
                        .answers(question.getAnswers())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ProfileQuestions> getProfileQuestionsBuilder(List<QuestionDataDto> profileByUserDto, Profiles profile) {
        return profileByUserDto.stream()
                .map(question -> ProfileQuestions.builder()
                        .profile(profile)
                        .id(question.getId())
                        .sid(question.getSid())
                        .question(question.getQuestion())
                        .questionType(question.getQuestionType())
                        .answers(question.getAnswers())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ProfileQuestions> getProfileQuestionsBuilder(CreateProfileByGroupDto profileByGroupDto, Profiles profile) {
        return profileByGroupDto.getQuestions().stream()
                .map(question -> ProfileQuestions.builder()
                        .profile(profile)
                        .sid(question.getSid())
                        .question(question.getQuestion())
                        .questionType(question.getQuestionType())
                        .answers(question.getAnswers())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProfileByUser(CreateProfileByUserDto profileByUserDto) {
        Users user = this.getUserBuilder(profileByUserDto);
        userRepository.save(user);

        Profiles profile = this.getProfileBuilder(profileByUserDto);

        Long profileId = profileRepository.save(profile).getId();

        Profiles profileRaw = profileRepository.findById(profileId)
                .orElseThrow(() -> new InvalidException("id", profileId, Error.PROFILE_NOT_FOUND));

        List<ProfileQuestions> profileQuestions = getProfileQuestionsBuilder(profileByUserDto, profileRaw);

        for (val question : profileQuestions) {
            profileQuestionsRepository.save(question);
        }
    }

    @Transactional
    public void saveProfileByGroup(CreateProfileByGroupDto profileByGroupDto) {
        String userId = profileByGroupDto.getUserId();
        Long groupId = profileByGroupDto.getGroupId();
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidException("userId", userId, Error.ACCOUNT_NOT_FOUND));

         boolean isOwner =  groupRepository.existsByIdAndOwner(groupId, user);
        Profiles profile = this.getProfileBuilder(profileByGroupDto);

        UserGroups userGroup = UserGroups.builder()
                .user(userRepository.findByUserId(profileByGroupDto.getUserId())
                        .orElseThrow(() -> new InvalidException("userId", profileByGroupDto.getUserId(), Error.ACCOUNT_NOT_FOUND)))
                .group(groupRepository.findById(profileByGroupDto.getGroupId())
                        .orElseThrow(() -> new InvalidException("groupId", profileByGroupDto.getGroupId(), Error.GROUP_NOT_FOUND)))
                 .position(isOwner ? Position.OWNER : Position.MEMBER).build();
        userGroupRepository.save(userGroup);

        Long profileId = profileRepository.save(profile).getId();

        Profiles profileRaw = profileRepository.findById(profileId)
                .orElseThrow(() -> new InvalidException("id", profileId, Error.PROFILE_NOT_FOUND));

        List<ProfileQuestions> profileQuestions = getProfileQuestionsBuilder(profileByGroupDto, profileRaw);

        for (val question : profileQuestions) {
            profileQuestionsRepository.save(question);
        }
    }


    @Transactional(readOnly = true)
    public List<Profiles> getOtherProfileByUser(String userId, String toUserId) {
        List<Profiles> myProfiles = profileRepository.getProfileByUserId(userId);
        if (myProfiles == null) {
            throw new InvalidException("userId", userId, Error.PROFILE_NOT_FOUND);
        }

        List<Long> groupIds = myProfiles.stream()
                .map(profile -> profile.getGroup().getId())
                .collect(Collectors.toList());

        List<Profiles> toProfile = profileRepository.getOtherProfileByUserAndGroupId(toUserId, groupIds);
        if (toProfile == null) {
            throw new InvalidException("toUserId", toUserId, Error.PROFILE_NOT_FOUND);
        }

        return toProfile;
    }

    @Transactional(readOnly = true)
    public List<Profiles> getProfileByUserId(String userId) {
        return profileRepository.getProfileByUserId(userId);
    }

    public Map<String, String> getSignedUrl(String fileName) {
        validateImageExtension(fileName);

        val contentType = "image/" + getFileExtension(fileName);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(imageBucketName)
                .key(fileName)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(100))
                .putObjectRequest(objectRequest)
                .build();

        val preSignedRequest = s3Presigner.presignPutObject(presignRequest);
        val signedUrl = preSignedRequest.url().toString();

        return Map.of("signedUrl", signedUrl, "filename", "https://ddd-harmonika.s3.ap-northeast-2.amazonaws.com/" + fileName);
    }

    private void validateImageExtension(String fileName) {
        val regExp = "^(jpeg|jpg|png|gif|bmp)$";
        val extension = getFileExtension(fileName).toLowerCase();
        if (!Pattern.matches(regExp, extension)) {
            throw new IllegalArgumentException("Invalid image extension");
        }
    }

    private String getFileExtension(String fileName) {
        val splitFileName = fileName.split("\\.");
        return splitFileName[splitFileName.length - 1];
    }

    public void validateProfileGroupByUserIdAndGroupId(String userId, Long groupId) {
        boolean isRegisterUser = userRepository.existsByUserId(userId);
        if (isRegisterUser == false) {
            throw new InvalidException("userId", userId, Error.ACCOUNT_NOT_FOUND);
        }

        boolean isJoinGroup = profileRepository.existsByUserIdAndGroupId(userId, groupId);
        if (isJoinGroup == false) {
            throw new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND);
        }
    }

    public void validateProfileByUserId(String userId) {
        boolean isRegisterUser = userRepository.existsByUserId(userId);
        if (isRegisterUser == false) {
            throw new InvalidException("userId", userId, Error.ACCOUNT_NOT_FOUND);
        }
    }

    public void validateIsSetProfileByUserId(String userId) {
        boolean isJoinGroup = profileRepository.existsByUserIdAndGroupIdIsNull(userId);
        if (isJoinGroup) {
            throw new InvalidException("userId", userId, Error.PROFILE_DUPLICATION);
        }
    }

    public List<Profiles> getProfileByGroupId(String userId, Long groupId) {
        return profileRepository.getProfileByGroupId(userId, groupId);
    }

    @Transactional
    public void updateProfile(UpdateProfileDto profileDto) {
        Profiles profile = profileRepository.findByUserIdAndGroupId(profileDto.getUserId(), profileDto.getGroupId());
        if (profile == null) {
            throw new InvalidException("userId", profileDto.getUserId(), Error.PROFILE_NOT_FOUND);
        }

        profileRepository.saveProfileById(profile.getId(), profile);

        List<ProfileQuestions> profileQuestions = getProfileQuestionsBuilder(profileDto.getQuestions(), profile);

        for (ProfileQuestions question : profileQuestions) {
            profileQuestionsRepository.updateProfileQuestionsByProfileId(question.getId(), question);
        }
    }
}
