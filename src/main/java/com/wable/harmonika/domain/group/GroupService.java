package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.GroupUserBirthdayListResponse.UserBirthday;
import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.UserGroups;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ProfilesRepository profilesRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, ProfilesRepository profilesRepository,
            UserGroupRepository userGroupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.profilesRepository = profilesRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    public GroupListResponse findAll(Long userId) {
        List<UserGroups> userGroups = userGroupRepository.findAllByUser(new Users(null, null, null, null)); // todo userId
        List<Groups> groups = userGroups.stream()
                .map(UserGroups::getGroup)
                .collect(Collectors.toList());

        return GroupListResponse.of(groups);
    }

    public GroupUserBirthdayListResponse findAllBirthday(Long groupId) {
        List<UserGroups> userGroups = userGroupRepository.findAllByGroup(new Groups(groupId, null, null));
        List<Users> users = userGroups.stream()
                .map(UserGroups::getUser)
                .toList();

        List<UserBirthday> birthdays = users.stream()
                .map(UserBirthday::of)
                .collect(Collectors.toList());

        return new GroupUserBirthdayListResponse(birthdays);
    }

    public GroupMemberListResponse findAllMember(Long groupId, String lastName, int size) {

        // querydsl 예정
        List<Users> users;
        if (lastName == null) {
            users = userGroupRepository.findAllUserWithPaging(groupId, size);
        } else {
            users = userGroupRepository.findAllUserWithPaging(groupId, lastName, size);
        }

        List<Profiles> findProfiles = profilesRepository.findAllByUserInAndGroupId(users,
                groupId);
        Map<Long, String> profileImageByUserId = findProfiles.stream()
                .collect(Collectors.toMap(
                        profiles -> profiles.getUser().getId(),
                        Profiles::getProfileImageUrl
                ));


        List<UserGroups> findUserGroups = userGroupRepository.findAllByUserInAndGroup(
                users, new Groups(groupId, null, null));

        Map<Long, String> positionByUserId = findUserGroups.stream()
                .collect(Collectors.toMap(
                        userGroups -> userGroups.getUser().getId(),
                        UserGroups::getPosition
                ));

        List<MemberResponse> memberResponses = users.stream()
                .map(user -> new MemberResponse(user.getId(),
                        profileImageByUserId.get(user.getId()),
                        user.getName(), positionByUserId.get(user.getId()), user.getBirth()))
                .toList();

//        Long totalUsers = userGroupRepository.countByGroup(new Groups(groupId, null , null));

        return new GroupMemberListResponse(11L, memberResponses);

    }

    public void updateUserRole(Long groupId, UserRoleUpdateRequest request) {
        Users findUser = userRepository.findById(request.getUserId()).orElseThrow(); // todo argument resolver 제거 예정
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("저장되지 않은 group 입니다."));

        if (userGroupRepository.findByUserAndGroup(findUser, group).isEmpty()) {
            throw new RuntimeException("group에 저장된 유저가 아닙니다.");
        }

//        userGroupRepository.updateUserRole(findUser, group, request.getRole());

    }
}
