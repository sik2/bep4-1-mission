package com.back.boundedContext.member.in.controller;

import com.back.boundedContext.member.app.facade.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/api/v1/members")
@RequiredArgsConstructor
public class MemberControllerV1 {
    private final MemberFacade memberFacade;

    @GetMapping("randomSecureTip")
    @Transactional(readOnly = true)
    public String getRandomSecureTip() {
        return memberFacade
                .getRandomSecureTip();
    }


}