package com.back.boundedContext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSupport {
	private final MemberRepository memberRepository;

	public long count() {
		return memberRepository.count();
	}

	public Optional<Member> findByUserName(String username) {
		return memberRepository.findByUserName(username);
	}

	public Optional<Member> findById(int id) {
		return memberRepository.findById(id);
	}

}
