package com.back.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.back.global.eventPublisher.EventPublisher;

@Configuration
public class GlobalConfig {
    @Getter
    public static EventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        GlobalConfig.eventPublisher = eventPublisher;
    }
}