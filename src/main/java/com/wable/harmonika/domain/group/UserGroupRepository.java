package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.UserGroups;
import com.wable.harmonika.domain.user.entity.Users;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroups, Long>, UserGroupRepositoryCustom {

    List<UserGroups> findAllByUserInAndGroup(List<Users> users, Groups group);

    List<UserGroups> findAllByGroup(Groups group);

    List<UserGroups> findAllByUser(Users user);

    Integer countByGroup(Groups group);

//    @Transactional
//    @Modifying
//    @Query("update UserGroups u set u.position = :role where u.user = :user and u.group = :group")
//    void updateUserRole(@Param("user") Users user,@Param("group") Groups groupId, Role role);

    Optional<UserGroups> findByUserAndGroup(@Param("user")Users users, @Param("group")Groups group);
}
