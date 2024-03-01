package com.wable.harmonika.global.auth;


import com.nimbusds.jwt.JWTClaimsSet;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.repository.UserRepository;
import com.wable.harmonika.global.error.exception.ForbiddenException;
import com.wable.harmonika.global.error.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Users.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            // 예시로, 세션에서 사용자 객체를 가져오는 방법
            HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
            String authorization = httpServletRequest.getHeader("Authorization");
            if (authorization == null || authorization.equals("")) {
                throw new UnauthorizedException("401 Unauthorized !!");
            }
            String token = authorization.replaceAll("^Bearer( )*", "");
            if (new VerifyToken().verify(token) == false) {
                throw new UnauthorizedException("401 Unauthorized !!");
            }
            String username = AwsCognitoJwtParserUtil.getClaim(token, "cognito:username");


            Optional<Users> byUserId = userRepository.findByUserId(username);
            if (byUserId.isPresent()) {
                return byUserId.get();
            }
            return Users.builder().userId(username).build();

        } catch (Exception e) {
            throw new UnauthorizedException("401 Unauthorized !!");
        }
    }

    @Builder
    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}