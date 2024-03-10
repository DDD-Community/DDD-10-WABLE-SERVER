package com.wable.user_api.domain.profile.repository;

import com.wable.user_api.domain.profile.entity.Profiles;
import com.wable.user_api.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProfileRepository extends JpaRepository<Profiles, Long>, ProfileRepositoryCustom {

    List<Profiles> findAllByUserInAndGroupId(List<Users> users, Long groupId);
}