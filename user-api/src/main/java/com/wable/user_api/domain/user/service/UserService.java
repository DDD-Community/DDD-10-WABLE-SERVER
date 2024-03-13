package com.wable.user_api.domain.user.service;

import com.wable.user_api.domain.user.dto.SignUpReqDto;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.domain.user.exception.UserNotFoundException;
import com.wable.user_api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

}
