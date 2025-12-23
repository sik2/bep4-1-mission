package com.back.boundedContext.member.domain;

import com.back.boundedContext.post.domain.enums.ActivityType;

public record MemberScoreEvent(
        long memberId,
        ActivityType activityType
) {
}
