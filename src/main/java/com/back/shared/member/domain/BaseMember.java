package com.back.shared.member.domain;

import com.back.global.jpa.entity.BaseEntity;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor
public abstract class BaseMember extends BaseEntity {

	private String userName;
	private String password;
	private String nickName;
	private int activityScore;

	public BaseMember(String userName, String password, String nickName, int activityScore) {
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.activityScore = activityScore;
	}

	public boolean isSystem() {
		return "system".equals(userName);
	}
}
