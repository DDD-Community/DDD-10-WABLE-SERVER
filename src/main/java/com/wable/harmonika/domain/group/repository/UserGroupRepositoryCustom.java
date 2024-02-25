package com.wable.harmonika.domain.group.repository;

import com.wable.harmonika.domain.user.entity.Users;
import java.util.List;

public interface UserGroupRepositoryCustom {

    List<Users> findAllUserWithPaging(Long groupId, String lastName, String searchName, int size);

}
