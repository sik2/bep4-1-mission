package com.back.boundedContext.member.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.member.domain.Member;
import com.back.global.rsData.RsData;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;
    private final MemberSupporter memberSupporter;

    public RsData<Member> join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    public long count() {
        return memberSupporter.count();
    }

    public Optional<Member> findByUsername(String username) {
        return memberSupporter.findByUsername(username);
    }

    public Optional<Member> findById(Integer id) {
        return memberSupporter.findById(id);
    }

    public String getRandomSecureTip() {
        return memberGetRandomSecureTipUseCase.getRandomSecureTip();
    }

    public void incrementActivityScore(int authorId, int score) {
        Member member = memberSupporter.findById(authorId)
                                       .orElseThrow();
        member.increaseActivityScore(score);
    }
}
