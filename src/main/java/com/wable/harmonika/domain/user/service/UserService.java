package com.wable.harmonika.domain.user.service;

import com.wable.harmonika.domain.user.dto.SignUpReqDto;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.exception.EmailDuplicationException;
import com.wable.harmonika.domain.user.exception.UserNotFoundException;
import com.wable.harmonika.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository UserRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return UserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", id));
    }

}
