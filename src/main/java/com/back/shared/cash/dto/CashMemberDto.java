package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashMemberDto {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private final String userName;
	private final String nickName;
	private int activityScore;
}
