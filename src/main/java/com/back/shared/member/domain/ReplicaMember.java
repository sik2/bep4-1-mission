package com.back.shared.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember{
	@Id
	private int id;
	private LocalDateTime createTime;
	private LocalDateTime modifyTime;

	public ReplicaMember(int id, LocalDateTime createTime, LocalDateTime modifyTime, String userName, String password, String nickName, int activityScore) {
		super(userName, password, nickName, activityScore);
		this.id = id;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}
}
