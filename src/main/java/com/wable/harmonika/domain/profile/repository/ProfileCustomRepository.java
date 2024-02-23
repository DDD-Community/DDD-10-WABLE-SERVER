package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.Profiles;

public interface ProfileCustomRepository {
    boolean existsByUserIdAndGroupIdIsNull(String userID);

    boolean existsByUserIdAndGroupId(String userID, Long groupId);

    Profiles getProfileByGroupId(Long groupId);

    Profiles getProfileByUserId(String userId);
}
