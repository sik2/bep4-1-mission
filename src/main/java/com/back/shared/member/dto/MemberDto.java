package com.back.shared.member.dto;

import java.time.LocalDateTime;

public record MemberDto(
        Integer id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String username,
        String nickname,
        String password,
        int activityScore
) { }
