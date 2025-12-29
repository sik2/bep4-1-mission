package com.back.global.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.back.global.global.GlobalConfig;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
	public String getModelTypeCode() {
		return this.getClass().getSimpleName();
	}

	protected void publishEvent(Object event) {
		GlobalConfig.getEventPublisher().publish(event);
	}

	public abstract int getId();
	public abstract LocalDateTime getCreateTime();
	public abstract LocalDateTime getModifyTime();
}
