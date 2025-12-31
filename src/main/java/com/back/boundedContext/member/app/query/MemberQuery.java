package com.back.boundedContext.member.app.query;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQuery {

    private final MemberRepository memberRepository;

    @Transactional
    public long count() {
        return memberRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

}
