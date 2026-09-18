package com.back.boundedContext.member.event;

import com.back.boundedContext.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinedEvent {
    private final MemberDto member;
}
