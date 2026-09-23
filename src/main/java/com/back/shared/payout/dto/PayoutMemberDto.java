package com.back.shared.payout.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayoutMemberDto {
    private final int id;
    private final String username;
    private final String nickname;
    private final int activityScore;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
}
