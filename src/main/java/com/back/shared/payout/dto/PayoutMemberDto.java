package com.back.shared.payout.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PayoutMemberDto {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private final String username;
	private final String nickname;
	private final int activityScore;
}
