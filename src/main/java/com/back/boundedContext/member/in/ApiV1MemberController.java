package com.back.boundedContext.member.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.boundedContext.member.app.MemberFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
	private final MemberFacade memberFacade;

	@GetMapping("randomSecureTip")
	public String getRandomSecuretip() {
		return memberFacade.getRandomSecureTip();
	}
}
