package com.wable.harmonika.domain.group.repository;

import com.wable.harmonika.domain.group.entity.GroupQuestion;
import com.wable.harmonika.domain.group.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupQuestionRepository extends JpaRepository<GroupQuestion, Long> {

    @Modifying
    void deleteAllByGroup(Groups groups);
}
