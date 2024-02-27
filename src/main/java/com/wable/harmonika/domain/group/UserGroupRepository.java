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
public interface UserGroupRepository extends JpaRepository<UserGroups, Long> {

    List<UserGroups> findAllByUserInAndGroup(List<Users> users, Groups group);

    List<UserGroups> findAllByGroup(Groups group);

    List<UserGroups> findAllByUser(Users user);

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

//    Integer countByGroup(Groups group);

//    @Transactional
//    @Modifying
//    @Query("update UserGroups u set u.position = :role where u.user = :user and u.group = :group")
//    void updateUserRole(@Param("user") Users user,@Param("group") Groups groupId, Role role);

    Optional<UserGroups> findByUserAndGroup(@Param("user")Users users, @Param("group")Groups group);
}
