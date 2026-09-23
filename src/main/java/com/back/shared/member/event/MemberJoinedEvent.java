package com.back.shared.member.event;


import com.back.shared.member.dto.MemberDto;

public class MemberJoinedEvent {
    MemberDto memberDto;

    public MemberJoinedEvent(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    public MemberDto getMemberDto() {
        return this.memberDto;
    }
}
