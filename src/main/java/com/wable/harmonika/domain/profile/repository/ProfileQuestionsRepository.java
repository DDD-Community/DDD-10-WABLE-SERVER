package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.ProfileQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileQuestionsRepository extends JpaRepository<ProfileQuestions, Long> {
}