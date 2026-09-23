package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;

public class MemberModifiedEvent {
    MemberDto memberDto;

    public MemberModifiedEvent(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    public MemberDto getMemberDto() {
        return this.memberDto;
    }
}
