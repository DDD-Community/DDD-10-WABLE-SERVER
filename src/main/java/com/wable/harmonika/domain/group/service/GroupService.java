package com.wable.harmonika.domain.group.service;

import com.wable.harmonika.domain.group.dto.UserResponse;
import com.wable.harmonika.domain.group.dto.UserPositionUpdateRequest;
import com.wable.harmonika.domain.group.dto.GroupListResponse;
import com.wable.harmonika.domain.group.dto.GroupModifyRequest;
import com.wable.harmonika.domain.group.dto.GroupModifyRequest.FixedQuestion;
import com.wable.harmonika.domain.group.dto.GroupUserBirthdayListResponse;
import com.wable.harmonika.domain.group.dto.GroupUserBirthdayListResponse.UserBirthday;
import com.wable.harmonika.domain.group.dto.GroupUserListResponse;
import com.wable.harmonika.domain.group.entity.*;
import com.wable.harmonika.domain.group.repository.*;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.wable.harmonika.global.error.Error;
import com.wable.harmonika.global.error.exception.InvalidException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ProfileRepository profileRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final QuestionsRepository questionRepository;
    private final GroupQuestionRepository groupQuestionRepository;

    public GroupService(GroupRepository groupRepository, ProfileRepository profileRepository,
            UserGroupRepository userGroupRepository, UserRepository userRepository,
            QuestionsRepository questionRepository,
            GroupQuestionRepository groupQuestionRepository) {
        this.groupRepository = groupRepository;
        this.profileRepository = profileRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.groupQuestionRepository = groupQuestionRepository;
    }

    public GroupListResponse findAll(Users users) {
        List<UserGroups> userGroups = userGroupRepository.findAllByUser(users);
        List<Groups> groups = userGroups.stream()
                .map(UserGroups::getGroup)
                .collect(Collectors.toList());

        return GroupListResponse.of(groups);
    }

    public GroupUserBirthdayListResponse findAllBirthday(Long groupId) {
        Optional<Groups> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND);
        }

        List<UserGroups> userGroups = userGroupRepository.findAllByGroup(group.get());
        List<Users> users = userGroups.stream()
                .map(UserGroups::getUser)
                .toList();

        List<UserBirthday> birthdays = users.stream()
                .map(UserBirthday::of)
                .collect(Collectors.toList());

        return new GroupUserBirthdayListResponse(birthdays);
    }

    public GroupUserListResponse findAllMember(Long groupId, String lastName, String searchName, int size) {
        List<Users> users = userGroupRepository.findAllUserWithPaging(groupId, lastName, searchName,
                size);

        List<Profiles> findProfiles = profileRepository.findAllByUserInAndGroupId(users, groupId);
        Map<Long, String> profileImageByUserId = findProfiles.stream()
                .collect(Collectors.toMap(
                        profiles -> profiles.getUser().getId(),
                        Profiles::getProfileImageUrl
                ));

        Groups group = new Groups(groupId, null, null);

        List<UserGroups> findUserGroups = userGroupRepository.findAllByUserInAndGroup(
                users, group);

        Map<String, Position> positionByUserId = findUserGroups.stream()
                .collect(Collectors.toMap(
                        userGroups -> userGroups.getUser().getUserId(),
                        UserGroups::getPosition
                ));

        List<UserResponse> userRespons = users.stream()
                .map(user -> new UserResponse(user.getUserId(),
                        profileImageByUserId.get(user.getId()),
                        user.getName(), positionByUserId.get(user.getUserId()), user.getBirth()))
                .toList();

        Integer count = userGroupRepository.countByGroup(group);

        return new GroupUserListResponse(count, userRespons);

    }

    public void updateUserRole(Long groupId, UserPositionUpdateRequest request, Users user) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND));

        if (userGroupRepository.findByUserAndGroup(user, group).isEmpty()) {
            throw new InvalidException("groupId", groupId, Error.GROUP_USER_NOT_FOUND);
        }
        Users targetUser = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new InvalidException("userId", request.getUserId(),
                        Error.GROUP_USER_NOT_FOUND));

        userGroupRepository.updateUserRole(targetUser, group, request.getPosition());
    }

    @Transactional
    public void createGroup(Users user, GroupModifyRequest request) {
        Users userBuilder = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new InvalidException("userId", user.getUserId(),
                        Error.ACCOUNT_NOT_FOUND));

        Groups groupBuilder = Groups.builder()
                .name(request.getName())
                .owner(userBuilder)
                .build();

        Groups group = groupRepository.save(groupBuilder);

        saveGroupQuestions(request, group);
    }

    @Transactional
    public void updateGroup(GroupModifyRequest request, Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow();
        groupQuestionRepository.deleteAllByGroup(group);

        saveGroupQuestions(request, group);

    }

    private void saveGroupQuestions(GroupModifyRequest request, Groups group) {
        List<Questions> newQuestions = request.getRequiredQuestions().stream()
                .map(s -> new Questions(null, QuestionNames.CUSTOM, s,
                        QuestionTypes.OPEN_ENDED, null))
                .collect(Collectors.toList());

        questionRepository.saveAll(newQuestions);

        List<Questions> fixedQuestions = questionRepository.findBySidIn(
                List.of(QuestionNames.MBTI, QuestionNames.NICKNAME, QuestionNames.HOBBY));

        List<GroupQuestion> fixedGroupQuestions = fixedQuestions.stream()
                .map(savedFixedQuestion -> {
                    FixedQuestion reqeustFixedQuestion = request.getFixedQuestions().stream()
                            .filter(fq -> fq.getId().equals(savedFixedQuestion.getId()))
                            .findAny()
                            .orElseThrow();

                    return new GroupQuestion(null, group, savedFixedQuestion,
                            reqeustFixedQuestion.isRequired());
                })
                .toList();

        List<GroupQuestion> groupQuestions = newQuestions.stream()
                .map(questions -> new GroupQuestion(null, group, questions, true))
                .collect(Collectors.toList());

        groupQuestions.addAll(fixedGroupQuestions);
        groupQuestionRepository.saveAll(groupQuestions);
    }

    public void validatorGroupOwner(Users user, Long groupId) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND));

        Users findUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new InvalidException("userId", user.getUserId(),
                        Error.ACCOUNT_NOT_FOUND));

        UserGroups userGroups = userGroupRepository.findByUserAndGroup(findUser, group)
                .orElseThrow(
                        () -> new InvalidException("groupId", groupId, Error.GROUP_USER_NOT_FOUND));

        if (!userGroups.getPosition().equals(Position.OWNER)) {
            throw new InvalidException("groupId", groupId, Error.GROUP_NOT_OWNER);
        }
    }
}
