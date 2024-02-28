package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProfileRepository extends JpaRepository<Profiles, Long>, ProfileRepositoryCustom {

    List<Profiles> findAllByUserInAndGroupId(List<Users> users, Long groupId);
}