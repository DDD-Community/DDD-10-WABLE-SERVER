package com.wable.user_api.domain.profile.repository;

import com.wable.user_api.domain.profile.entity.Profiles;

import java.util.List;

public interface ProfileRepositoryCustom {
    boolean existsByUserIdAndGroupIdIsNull(String userID);

    boolean existsByUserIdAndGroupId(String userID, Long groupId);

    List<Profiles> getProfileByGroupId(String UserId, Long groupId);

    List<Profiles> getProfileByUserId(String userId);

    List<Profiles> getOtherProfileByUserAndGroupId(String toUserId, List<Long> groupId);

    Profiles findByUserIdAndGroupId(String userId, Long groupId);

    void saveProfileById(Long id, Profiles profile);

}
