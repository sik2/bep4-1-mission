package com.back.shared.member.dto;

import java.time.LocalDateTime;

import com.back.boundedContext.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {
	private final int id;
	private final LocalDateTime createDate;
	private final LocalDateTime modifyDate;
	private final String username;
	private final String nickname;
	private final int activityScore;
}
