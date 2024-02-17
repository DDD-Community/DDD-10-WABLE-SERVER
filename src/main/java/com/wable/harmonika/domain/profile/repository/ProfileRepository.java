package com.wable.harmonika.domain.profile.repository;

import com.wable.harmonika.domain.profile.entity.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profiles, Long> {
}