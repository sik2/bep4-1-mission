package com.back.global.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public abstract class BaseManualAndTime extends BaseEntity {
	@Id
	private int id;
	@CreatedDate
	private LocalDateTime createTime;
	@LastModifiedDate
	private LocalDateTime modifyTime;


	public BaseManualAndTime(int id) {
		this.id = id;
	}
}
