package com.wable.harmonika.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "와블와블",
                description = "DDD 10 기 웹2팀 - 와블와블 API 명세서입니다.",
                version = "v1",
                contact = @Contact(
                        name = "DDD 10 기 웹2팀 - 와블와블",
                        url = "https://github.com/DDD-Community/DDD-10-WABLE-SERVER"
                ),
                license = @License(name = "Apache 2.0", url = "https://springdoc.org/")),
        security = {
                @SecurityRequirement(name = "JWT Authentication")
        },
        servers = {
                @Server(url = "http://localhost:8080/api", description = "Local Server"),
                @Server(url = "https://harmonika.wo.tc/api", description = "Dev Server")
        }
)
@SecurityScheme(
        name = "JWT Authentication",
        type = SecuritySchemeType.HTTP,
        description = "JWT 인증을 위한 헤더. Bearer Authentication",
        paramName = "Authorization",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
@RequiredArgsConstructor
public class Swagger3Configuration {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("와블와블 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
