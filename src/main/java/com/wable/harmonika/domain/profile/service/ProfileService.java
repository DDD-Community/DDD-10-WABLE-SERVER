package com.wable.harmonika.domain.profile.service;

import com.wable.harmonika.domain.group.GroupRepository;
import com.wable.harmonika.domain.profile.dto.CreateProfileByGroupDto;
import com.wable.harmonika.domain.profile.dto.CreateProfileByUserDto;
import com.wable.harmonika.domain.profile.dto.QuestionDataDto;
import com.wable.harmonika.domain.profile.dto.UpdateProfileDto;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.repository.ProfileQuestionsRepository;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import com.wable.harmonika.global.error.Error;
import com.wable.harmonika.global.error.exception.InvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.minidev.asm.ConvertDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Arrays;
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
    private final S3Presigner s3Presigner;

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
                .nickname(profileByUserDto.getNickName())
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
                .nickname(profileByUserDto.getNickName())
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
        Profiles profile = this.getProfileBuilder(profileByGroupDto);

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
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        val preSignedRequest = s3Presigner.presignPutObject(presignRequest);
        val signedUrl = preSignedRequest.url().toString();

        return Map.of("signedUrl", signedUrl, "filename", fileName);
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
