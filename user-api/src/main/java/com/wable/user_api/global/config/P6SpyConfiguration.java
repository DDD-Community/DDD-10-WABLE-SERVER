package com.wable.user_api.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.wable.user_api.global.formatter.P6SpyFormatter;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6SpyConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
    }
}