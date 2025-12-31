package com.back.boundedContext.member.in;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberFacade memberFacade;

    @GetMapping("/randomSecureTip")
    public RsData<String> getRandomSecureTip() {
        return new RsData<>("200", "", memberFacade.getRandomSecureTip());
    }

    @PostMapping("{id}")
    public RsData<Member> modifyMember(@PathVariable int id, @RequestBody MemberDto memberDto) {
        return memberFacade.modify(memberDto);
    }
}
