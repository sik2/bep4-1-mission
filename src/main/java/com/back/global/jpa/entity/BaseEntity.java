package com.back.global.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.back.global.global.GlobalConfig;
import com.back.standard.modelType.CanGetModelTypeCode;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity implements CanGetModelTypeCode {
	protected void publishEvent(Object event) {
		GlobalConfig.getEventPublisher().publish(event);
	}

	public abstract int getId();
	public abstract LocalDateTime getCreateTime();
	public abstract LocalDateTime getModifyTime();

	@Override
	public String getModelTypeCode() {
		return this.getClass().getSimpleName();
	}
}
