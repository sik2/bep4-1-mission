package com.back.shared.market.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MarketMemberDto {
	private final int id;
	private final LocalDateTime crateTime;
	private final LocalDateTime modifyTime;
	private final String userName;
	private final String nickName;
	private final int activityScore;
}
