package com.wable.harmonika.domain.group.repository;

import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.user.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {

    List<Profiles> findAllByUserInAndGroupId(List<Users> users, Long groupId);
}
