package com.wable.harmonika.domain.profile.service;

import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.exception.ProfileNotFoundException;
import com.wable.harmonika.domain.profile.repository.ProfileRepository;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository ProfileRepository;

    private final UserRepository UserRepository;

    @Transactional(readOnly = true)
    public Profiles getProfile(Long id) {
        return ProfileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("id", id));
    }

    public Users getUserProfileByUserId(Long id) {
        return UserRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("id", id));
    }
}