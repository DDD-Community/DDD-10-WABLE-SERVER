package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.UserGroups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserGroupRepository extends JpaRepository<Long, UserGroups> {

    List<UserGroups> findAllByUserInAndGroupId(List<Users> users, Long groupId);


    List<UserGroups> findAllByGroupId(Long groupId);

    List<UserGroups> findAllByUserId(Long userId);

    @Query(nativeQuery = true,
            value = "select * from users u "
                    + "join user_groups g on u.user_id = g.user_id "
                    + "where u.name > :lastName and g.group_id = :groupId "
                    + "limit :size ")
    List<Users> findAllUserWithPaging(@Param("groupId") Long groupId, @Param("lastName") String lastName, @Param("size") int size);

    @Query(nativeQuery = true,
            value = "select * from users u "
                    + "join user_groups g on u.user_id = g.user_id "
                    + "where g.group_id = :groupId "
                    + "limit :size ")
    List<Users> findAllUserWithPaging(@Param("groupId") Long groupId, @Param("size") int size);

    Long countByGroupId(Long groupId);

    @Transactional
    @Modifying
    @Query("update UserGroups u set u.position = :role where u.user = :user and u.group = :group")
    void updateUserRole(Users user, Groups groupId, Role role);

    Optional<UserGroups> findByUserAndGroup(@Param("user")Users users, @Param("group")Groups group);
}
