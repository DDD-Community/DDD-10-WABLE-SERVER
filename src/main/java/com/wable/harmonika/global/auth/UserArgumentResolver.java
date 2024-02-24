package com.wable.harmonika.global.auth;


import com.nimbusds.jwt.JWTClaimsSet;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.error.exception.ForbiddenException;
import com.wable.harmonika.global.error.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.wable.harmonika.global.auth.AwsCognitoJwtValidatorUtil.validateAWSJwtToken;


public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Users.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
        try {
            // 예시로, 세션에서 사용자 객체를 가져오는 방법
            HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
            String authorization = httpServletRequest.getHeader("Authorization");
            if (authorization == null || authorization.equals("")){
                throw new UnauthorizedException("401 Unauthorized !!");
            }
            String token = authorization.replaceAll("^Bearer( )*", "");
            if (!VerifyToken.verify(token)){
                throw new UnauthorizedException("401 Unauthorized !!");
            }
            String username = AwsCognitoJwtParserUtil.getClaim(token, "cognito:username");

            return   Users.builder()
                    .userId(username)
                    .build(); // Users 객체를 빌드
        } catch (Exception e) {
            throw new UnauthorizedException("401 Unauthorized !!");
        }
    }
}