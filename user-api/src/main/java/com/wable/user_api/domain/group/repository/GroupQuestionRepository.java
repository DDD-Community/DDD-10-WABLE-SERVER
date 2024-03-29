package com.wable.user_api.domain.group.repository;

import com.wable.user_api.domain.group.entity.GroupQuestion;
import com.wable.user_api.domain.group.entity.Groups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupQuestionRepository extends JpaRepository<GroupQuestion, Long> {

    @Modifying
    void deleteAllByGroup(Groups groups);

    List<GroupQuestion> findALlByGroup(Groups groups);
}
