package com.wable.harmonika.domain.group.repository;

import com.wable.harmonika.domain.group.entity.Groups;

import com.wable.harmonika.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
     boolean existsByIdAndOwner(Long groupId, Users owner);

        boolean existsByName(String name);
}
