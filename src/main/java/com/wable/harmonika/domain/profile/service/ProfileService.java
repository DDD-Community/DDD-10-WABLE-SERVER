package com.wable.harmonika.domain.profile.service;

import com.wable.harmonika.domain.profile.dto.CreateProfileByUserDto;
import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.exception.ProfileNotFoundException;
import com.wable.harmonika.domain.profile.repository.ProfileQuestionsRepository;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.bucket}")
    private String imageBucketName;

    public void validateProfileByUser(CreateProfileByUserDto profileByUserDto) {
        boolean isRegisterUser = userRepository.existsByUserId(profileByUserDto.getUserId());
        if (isRegisterUser) {
            throw new IllegalArgumentException("User already exists");
        }

        boolean hasGroup = profileRepository.existsByUserIdAndGroupIdIsNull(profileByUserDto.getUserId());
        if (hasGroup) {
            throw new IllegalArgumentException("Profile already exists");
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

    public Map<String, String> getSignedUrl(Long userId, String fileName) {
        validateImageExtension(fileName);

        val keyName = "/" + userId + "/" + UUID.randomUUID() + "-" + fileName;
        val contentType = "image/" + getFileExtension(fileName);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(imageBucketName)
                .key(keyName)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        val preSignedRequest = s3Presigner.presignPutObject(presignRequest);
        val signedUrl = preSignedRequest.url().toString();

        return Map.of("signedUrl", signedUrl, "filename", keyName);
    }

    private void validateImageExtension(String fileName) {
        val regExp = "^(jpeg|png|gif|bmp)$";
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
