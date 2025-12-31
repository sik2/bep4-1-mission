package com.back.boundedContext.member.app.facade;


import com.back.boundedContext.member.app.query.MemberQuery;
import com.back.boundedContext.member.app.usecase.MemberJoinUseCase;
import com.back.boundedContext.member.app.usecase.RandomTipUseCase;
import com.back.boundedContext.member.domain.Member;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
   private final MemberJoinUseCase memberJoinUseCase;

   private final MemberQuery memberQuery;
   private final RandomTipUseCase randomTipUseCase;

   @Transactional
   public long count() {
        return memberQuery.count();
    }

   @Transactional
    public RsData<Member> join(String username, String password, String nickname){
        return memberJoinUseCase.join(username, password, nickname);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberQuery.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberQuery.findById(id);
    }

    @Transactional
    public Member save(Member member) {
        return memberQuery.save(member);
    }

    public String getRandomSecureTip() {
        return randomTipUseCase.getRandomSecureTip();
    }

}
