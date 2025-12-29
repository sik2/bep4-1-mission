package com.back.boundedContext.cash.domain;

import java.time.LocalDateTime;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASH_MEMBER")
@Getter
@NoArgsConstructor
public class CashMember extends ReplicaMember {
	public CashMember(int id, LocalDateTime createTime, LocalDateTime modifyTime, String userName, String password,
		String nickName, int activityScore) {
		super(id, createTime, modifyTime, userName, password, nickName, activityScore);
	}

	public CashMemberDto toDto() {
		return new CashMemberDto(
			getId(),
			getCreateTime(),
			getModifyTime(),
			getUserName(),
			getNickName(),
			getActivityScore()
		);
	}
}
