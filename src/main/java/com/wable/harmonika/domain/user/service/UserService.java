package com.wable.harmonika.domain.user.service;

import com.wable.harmonika.domain.user.dto.SignUpReqDto;
import com.wable.harmonika.domain.user.entity.User;
import com.wable.harmonika.domain.user.exception.EmailDuplicationException;
import com.wable.harmonika.domain.user.exception.UserNotFoundException;
import com.wable.harmonika.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository UserRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return UserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", id));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return UserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return UserRepository.existsByEmail(email);
    }

    public Long signUp(SignUpReqDto requestDto) {
        if (isExistedEmail(requestDto.getEmail())) {
            throw new EmailDuplicationException(requestDto.getEmail());
        }

        String encodedPassword = encodePassword(requestDto.getPassword());
        User user = UserRepository.save(requestDto.toEntity(encodedPassword));
        return user.getId();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
