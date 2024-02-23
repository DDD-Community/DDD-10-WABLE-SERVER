package com.wable.harmonika.domain.profile.repository;

public interface ProfileCustomRepository {
    boolean existsByUserIdAndGroupIdIsNull(String userID);
}
