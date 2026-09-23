package com.back.global.jpa;

import java.time.LocalDateTime;

import com.back.global.jpa.entity.BaseEntity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseIdAndTimeManual extends BaseEntity {
	@Id
	private int id;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
}
