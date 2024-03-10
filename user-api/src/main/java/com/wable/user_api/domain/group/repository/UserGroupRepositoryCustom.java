package com.wable.user_api.domain.group.repository;

import com.wable.user_api.domain.user.entity.Users;
import java.util.List;

public interface UserGroupRepositoryCustom {

    List<Users> findAllUserWithPaging(Long groupId, String lastName, String searchName, int size);

}
