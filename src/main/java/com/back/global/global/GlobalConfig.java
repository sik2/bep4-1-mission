package com.back.global.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.back.global.eventPublisher.EventPublisher;

import lombok.Getter;

@Configuration
public class GlobalConfig {

	@Getter
	private static EventPublisher eventPublisher;

	@Autowired
	public void setEventPublisher(EventPublisher eventPublisher) {
		GlobalConfig.eventPublisher = eventPublisher;
	}
}
