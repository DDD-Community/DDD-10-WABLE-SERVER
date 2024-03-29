package com.wable.user_api.domain.group.service;

import com.wable.user_api.domain.group.dto.GroupDetailResponse;
import com.wable.user_api.domain.group.dto.GroupListResponse;
import com.wable.user_api.domain.group.dto.GroupModifyRequest;
import com.wable.user_api.domain.group.dto.GroupModifyRequest.FixedQuestion;
import com.wable.user_api.domain.group.dto.GroupUserBirthdayListResponse;
import com.wable.user_api.domain.group.dto.GroupUserBirthdayListResponse.UserBirthday;
import com.wable.user_api.domain.group.dto.GroupUserListResponse;
import com.wable.user_api.domain.group.dto.QuestionsResponse;
import com.wable.user_api.domain.group.dto.UserPositionResponse;
import com.wable.user_api.domain.group.dto.UserPositionUpdateRequest;
import com.wable.user_api.domain.group.dto.UserResponse;
import com.wable.user_api.domain.group.entity.GroupQuestion;
import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.group.entity.Position;
import com.wable.user_api.domain.group.entity.QuestionNames;
import com.wable.user_api.domain.group.entity.QuestionTypes;
import com.wable.user_api.domain.group.entity.Questions;
import com.wable.user_api.domain.group.entity.UserGroups;
import com.wable.user_api.domain.group.repository.GroupQuestionRepository;
import com.wable.user_api.domain.group.repository.GroupRepository;
import com.wable.user_api.domain.group.repository.QuestionsRepository;
import com.wable.user_api.domain.group.repository.UserGroupRepository;
import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.profile.repository.ProfileRepository;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.domain.user.repository.UserRepository;
import com.wable.user_api.global.error.Error;
import com.wable.user_api.global.error.exception.InvalidException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public UserPositionResponse getUserPosition(Users user, Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow(
                () -> new InvalidException("group id ", groupId, Error.GROUP_NOT_FOUND));

        UserGroups userGroup = userGroupRepository.findByUserAndGroup(user, group).orElseThrow(
                () -> new InvalidException("groupId", groupId, Error.GROUP_USER_NOT_FOUND));

        return new UserPositionResponse(userGroup.getPosition().equals(Position.OWNER));
    }

    public GroupUserListResponse findAllMember(Long groupId, String lastName, String searchName,
            int size,
            Users requestUser) {
        List<Users> users = userGroupRepository.findAllUserWithPaging(groupId, lastName, searchName,
                size);

        List<Profiles> findProfiles = profileRepository.findAllByUserInAndGroupId(users, groupId);
        Map<Long, String> profileImageByUserId = findProfiles.stream()
                .collect(Collectors.toMap(
                        profiles -> profiles.getUser().getId(),
                        Profiles::getProfileImageUrl
                ));

        Groups group = groupRepository.findById(groupId).orElseThrow(
                () -> new InvalidException("group id ", groupId, Error.GROUP_NOT_FOUND));

        List<UserGroups> findUserGroups = userGroupRepository.findAllByUserInAndGroup(
                users, group);

        Map<String, Position> positionByUserId = findUserGroups.stream()
                .collect(Collectors.toMap(
                        userGroups -> userGroups.getUser().getUserId(),
                        UserGroups::getPosition
                ));

        List<UserResponse> userResponse = users.stream()
                .map(user -> new UserResponse(user.getUserId(),
                        profileImageByUserId.get(user.getId()),
                        user.getName(), positionByUserId.get(user.getUserId()), user.getBirth()))
                .toList();

        Integer count = userGroupRepository.countByGroup(group);

        boolean isOwner = userGroupRepository.findByUserAndGroup(requestUser, group).get()
                .getPosition()
                .equals(Position.OWNER);
        return new GroupUserListResponse(count, isOwner, userResponse);
    }

    public void updateUserRole(Long groupId, UserPositionUpdateRequest request, Users user) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND));

        Users targetUser = userRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new InvalidException("userId", request.getUserId(),
                        Error.GROUP_USER_NOT_FOUND)
        );

        if (userGroupRepository.findByUserAndGroup(targetUser, group).isEmpty()) {
            throw new InvalidException("groupId", groupId, Error.GROUP_USER_NOT_FOUND);
        }

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

        userGroupRepository.save(new UserGroups(user, group, Position.OWNER));

    }

    @Transactional
    public void updateGroup(GroupModifyRequest request, Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow();
        groupQuestionRepository.deleteAllByGroup(group);

    }

    public GroupDetailResponse findGroup(Long groupId) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidException("groupId", groupId, Error.GROUP_NOT_FOUND));

        List<GroupQuestion> groupQuestions = groupQuestionRepository.findALlByGroup(group);
        Map<Long, Boolean> isRequiredByQuestionId = groupQuestions.stream()
                .collect(Collectors.toMap(
                        groupQuestion -> groupQuestion.getQuestion().getId(),
                        GroupQuestion::isRequired
                ));

        List<Questions> questions = groupQuestions.stream()
                .map(GroupQuestion::getQuestion)
                .toList();

        List<QuestionsResponse> response = questions.stream()
                .map(question -> QuestionsResponse.of(question,
                        isRequiredByQuestionId.get(question.getId())))
                .collect(Collectors.toList());

        return new GroupDetailResponse(groupId, group.getName(), response);
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

    public void validatorGroupName(String name) {
        if (groupRepository.existsByName(name)) {
            throw new InvalidException("name", name, Error.GROUP_NAME_DUPLICATE);
        }
    }
}
