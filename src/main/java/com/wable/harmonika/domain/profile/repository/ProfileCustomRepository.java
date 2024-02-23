package com.wable.harmonika.domain.profile.repository;

public interface ProfileCustomRepository {
    boolean existsByUserIdAndGroupIdIsNull(String userID);

    boolean existsByUserIdAndGroupId(String userID, Long groupId);
}
