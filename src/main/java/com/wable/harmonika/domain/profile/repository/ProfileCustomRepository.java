package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.Profiles;

import java.util.List;

public interface ProfileCustomRepository {
    boolean existsByUserIdAndGroupIdIsNull(String userID);

    boolean existsByUserIdAndGroupId(String userID, Long groupId);

    List<Profiles> getProfileByGroupId(String UserId, Long groupId);

    List<Profiles> getProfileByUserId(String userId);

    List<Profiles> getOtherProfileByUserAndGroupId(String toUserId, List<Long> groupId);

    Profiles findByUserIdAndGroupId(String userId, Long groupId);

    void saveProfileById(Long id, Profiles profile);

}
