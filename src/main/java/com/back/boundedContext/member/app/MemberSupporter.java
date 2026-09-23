package com.back.boundedContext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSupporter {
    private final MemberRepository memberRepository;

    public long count() {
        return memberRepository.count();
    }

    public Optional<Member> findById(Integer id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
