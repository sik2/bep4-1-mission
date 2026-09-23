package com.back.shared.cash.dto;

import java.time.LocalDateTime;

import com.back.boundedContext.cash.domain.CashMember;

import lombok.AllArgsConstructor;

public record CashMemberDto(int id, String nickname, String username, String password, int activityScore,
                            LocalDateTime createDate, LocalDateTime modifyDate) {

    public CashMemberDto(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username,
                         String nickname, int activityScore) {
        this(id, nickname, username, "", activityScore, createDate, modifyDate);
    }
}
