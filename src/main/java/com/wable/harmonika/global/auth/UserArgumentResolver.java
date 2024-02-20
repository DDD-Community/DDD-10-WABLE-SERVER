package com.wable.harmonika.global.auth;


import com.wable.harmonika.domain.user.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Users.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // 예시로, 세션에서 사용자 객체를 가져오는 방법
//        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();



        return Users.builder()
                .email("test")
                .name("test");
    }
//
//    @Override
//    public boolean verifyToken() {
//        return true;
//    }
}