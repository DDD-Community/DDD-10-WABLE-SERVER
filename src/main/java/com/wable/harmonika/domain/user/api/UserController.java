package com.wable.harmonika.domain.user.api;

import com.wable.harmonika.domain.user.dto.MyUserResDto;
import com.wable.harmonika.domain.user.dto.SignUpReqDto;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "계정 API")
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService UserService;

    @Operation(summary = "회원가입", description = "계정을 생성합니다.")
    @Parameters({@Parameter(name = "requestDto", description = "회원가입 요청 객체"),})
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyUserResDto signUp(@RequestBody @Valid final SignUpReqDto requestDto) {
        Long userId = UserService.signUp(requestDto);
        Users findUser = UserService.findById(userId);
        return new MyUserResDto(findUser);
    }
}
