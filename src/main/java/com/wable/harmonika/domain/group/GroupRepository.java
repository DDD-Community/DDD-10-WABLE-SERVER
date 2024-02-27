package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.group.entity.UserGroups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {

}
