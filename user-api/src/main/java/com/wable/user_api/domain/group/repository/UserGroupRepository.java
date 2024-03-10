package com.wable.user_api.domain.group.repository;

import com.wable.user_api.domain.group.entity.Groups;
import com.wable.user_api.domain.group.entity.Position;
import com.wable.user_api.domain.group.entity.UserGroups;
import com.wable.user_api.domain.user.entity.Users;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroups, Long>,
        UserGroupRepositoryCustom {

    List<UserGroups> findAllByUserInAndGroup(List<Users> users, Groups group);

    List<UserGroups> findAllByGroup(Groups group);

    List<UserGroups> findAllByUser(Users user);

    Integer countByGroup(Groups group);

    @Transactional
    @Modifying
    @Query("update UserGroups u set u.position = :position where u.user = :user and u.group = :group")
    void updateUserRole(@Param("user") Users user, @Param("group") Groups group, Position position);

    Optional<UserGroups> findByUserAndGroup(Users user, Groups group);
}
