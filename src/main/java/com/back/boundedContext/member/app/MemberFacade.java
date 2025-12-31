package com.back.boundedContext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.member.domain.Member;
import com.back.global.RsData.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberJoinUseCase memberJoinUseCase;
	private final MemberSupport memberSupport;
	private final MemberGetRandomSecureTip memberGetRandomSecureTip;

	@Transactional(readOnly = true)
	public long count() {
		return memberSupport.count();
	}

	@Transactional
	public RsData<Member> join(String userName, String password, String nickName) {

		return memberJoinUseCase.join(userName, password, nickName);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findByUserName(String userName) {

		return memberSupport.findByUserName(userName);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findById(int id) {
		return memberSupport.findById(id);
	}

	public String getRandomSecureTip() {
		return memberGetRandomSecureTip.getRandomSecureTip();
	}
}
