package com.back.boundedContext.market.domain;

import java.time.LocalDateTime;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_MEMBER")
@Getter
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
	public MarketMember(int id, LocalDateTime createTime, LocalDateTime modifyTime, String userName, String password, String nickName, int activityScore) {
		super(id, createTime, modifyTime, userName, password, nickName, activityScore);
	}

	public MarketMemberDto toDto() {
		return new MarketMemberDto(
			getId(),
			getCreateTime(),
			getModifyTime(),
			getUserName(),
			getNickName(),
			getActivityScore()
		);
	}
}
