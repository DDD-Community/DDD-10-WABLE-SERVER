package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.GroupModifyRequest.FixedQuestion;
import com.wable.harmonika.domain.group.GroupUserBirthdayListResponse.UserBirthday;
import com.wable.harmonika.domain.group.entity.GroupQuestion;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.QuestionNames;
import com.wable.harmonika.domain.group.entity.QuestionTypes;
import com.wable.harmonika.domain.group.entity.Questions;
import com.wable.harmonika.domain.group.entity.UserGroups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ProfilesRepository profilesRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final QuestionsRepository questionRepository;
    private final GroupQuestionRepository groupQuestionRepository;

    public GroupService(GroupRepository groupRepository, ProfilesRepository profilesRepository,
            UserGroupRepository userGroupRepository, UserRepository userRepository,
            QuestionsRepository questionRepository,
            GroupQuestionRepository groupQuestionRepository) {
        this.groupRepository = groupRepository;
        this.profilesRepository = profilesRepository;
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
        List<UserGroups> userGroups = userGroupRepository.findAllByGroup(
                new Groups(groupId, null, null));
        List<Users> users = userGroups.stream()
                .map(UserGroups::getUser)
                .toList();

        List<UserBirthday> birthdays = users.stream()
                .map(UserBirthday::of)
                .collect(Collectors.toList());

        return new GroupUserBirthdayListResponse(birthdays);
    }

    public GroupUserListResponse findAllMember(Long groupId, String lastName, String searchName,
            int size) {
        List<Users> users = userGroupRepository.findAllUserWithPaging(groupId, lastName, searchName,
                size);

        List<Profiles> findProfiles = profilesRepository.findAllByUserInAndGroupId(users, groupId);
        Map<Long, String> profileImageByUserId = findProfiles.stream()
                .collect(Collectors.toMap(
                        profiles -> profiles.getUser().getId(),
                        Profiles::getProfileImageUrl
                ));

        Groups group = new Groups(groupId, null, null);

        List<UserGroups> findUserGroups = userGroupRepository.findAllByUserInAndGroup(
                users, group);

        Map<Long, String> positionByUserId = findUserGroups.stream()
                .collect(Collectors.toMap(
                        userGroups -> userGroups.getUser().getId(),
                        UserGroups::getPosition
                ));

        List<UserResponse> userRespons = users.stream()
                .map(user -> new UserResponse(user.getUserId(),
                        profileImageByUserId.get(user.getId()),
                        user.getName(), positionByUserId.get(user.getId()), user.getBirth()))
                .toList();

        Integer count = userGroupRepository.countByGroup(group);

        return new GroupUserListResponse(count, userRespons);

    }

    public void updateUserRole(Long groupId, UserRoleUpdateRequest request, Users users) {
        Users findUser = userRepository.findById(request.getUserId())
                .orElseThrow(); // todo argument resolver 제거 예정
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("저장되지 않은 group 입니다."));

        if (userGroupRepository.findByUserAndGroup(findUser, group).isEmpty()) {
            throw new RuntimeException("group에 저장된 유저가 아닙니다.");
        }

//        userGroupRepository.updateUserRole(findUser, group, request.getRole());

    }

    @Transactional
    public void createGroup(Users user, GroupModifyRequest request) {
        Groups group = groupRepository.save(new Groups(null, request.getName(), user));

        saveGroupQuestions(request, group);
    }

    @Transactional
    public void updateGroup(Users users, GroupModifyRequest request, Long groupId) {
        // todo  users 권한 체크
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
}
