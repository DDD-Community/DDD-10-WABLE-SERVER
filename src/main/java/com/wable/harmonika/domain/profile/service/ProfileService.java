package com.wable.harmonika.domain.profile.service;

import com.wable.harmonika.domain.group.GroupRepository;
import com.wable.harmonika.domain.profile.dto.CreateProfileByGroupDto;
import com.wable.harmonika.domain.profile.dto.CreateProfileByUserDto;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.exception.ProfileNotFoundException;
import com.wable.harmonika.domain.profile.repository.ProfileQuestionsRepository;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import com.wable.harmonika.global.error.Error;
import com.wable.harmonika.global.error.exception.InvalidException;
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
import java.util.UUID;
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

    private Users getUserBuilder(CreateProfileByGroupDto profileByGroupDto) {
        return Users.builder()
                .userId(profileByGroupDto.getUserId())
                .name(profileByGroupDto.getName())
                .gender(profileByGroupDto.getGender())
                .birth(profileByGroupDto.getBirth())
                .build();
    }

    private Profiles getProfileBuilder(CreateProfileByUserDto profileByUserDto) {
        return Profiles.builder()
                .nickname(profileByUserDto.getNickName())
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
                .orElseThrow(() -> new ProfileNotFoundException("id", profileId));

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
                .orElseThrow(() -> new ProfileNotFoundException("id", profileId));

        List<ProfileQuestions> profileQuestions = getProfileQuestionsBuilder(profileByGroupDto, profileRaw);

        for (val question : profileQuestions) {
            profileQuestionsRepository.save(question);
        }
    }

    @Transactional(readOnly = true)
    public Profiles getGroupProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("id", id));
    }

    @Transactional(readOnly = true)
    public Users getUserProfileByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("id", id));
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
        val splittedFileName = fileName.split("\\.");
        return splittedFileName[splittedFileName.length - 1];
    }
}
