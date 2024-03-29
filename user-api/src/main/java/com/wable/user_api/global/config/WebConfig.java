package com.wable.user_api.global.config;

import com.wable.user_api.domain.user.repository.UserRepository;
import com.wable.user_api.global.auth.UserArgumentResolver;
import com.wable.user_api.global.auth.VerifyToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifyToken verifyToken;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(UserArgumentResolver.builder().
                userRepository(userRepository).
                verifyToken(verifyToken).
                build());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}