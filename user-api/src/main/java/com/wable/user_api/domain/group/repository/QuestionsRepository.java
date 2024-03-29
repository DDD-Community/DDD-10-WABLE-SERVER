package com.wable.user_api.domain.group.repository;

import com.wable.user_api.domain.group.entity.QuestionNames;
import com.wable.user_api.domain.group.entity.Questions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {

    List<Questions> findBySidIn(List<QuestionNames> sids);
}
