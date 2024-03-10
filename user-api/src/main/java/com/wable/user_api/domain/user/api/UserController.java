package com.wable.user_api.domain.user.api;

import com.wable.user_api.domain.user.dto.MyUserResDto;
import com.wable.user_api.domain.user.dto.SignUpReqDto;
import com.wable.user_api.domain.user.entity.Users;
import com.wable.user_api.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "계정 API (회원가입, 로그인 cognito로 대체)")
@Slf4j
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    cognito로 대체함

}
