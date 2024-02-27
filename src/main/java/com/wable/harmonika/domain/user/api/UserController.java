package com.wable.harmonika.domain.user.api;

import com.wable.harmonika.domain.user.dto.MyUserResDto;
import com.wable.harmonika.domain.user.dto.SignUpReqDto;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.service.UserService;
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
    @Operation(summary = "회원가입", description = "회원가입을 한다")
    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpReqDto signUpReqDto) {
        userService.signUp(signUpReqDto);
    }

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회한다")
    @GetMapping("/me")
    @ResponseStatus(value = HttpStatus.OK)
    public MyUserResDto getMyInfo(@Parameter(hidden = true) Users user) {
        return new MyUserResDto(user);
    }
}
