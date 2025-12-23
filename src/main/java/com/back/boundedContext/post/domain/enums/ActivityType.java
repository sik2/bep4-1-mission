package com.back.boundedContext.post.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
    POST(3),
    COMMENT(1);

    private final int score;
}
