package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.exception.DomainException;
import com.back.shared.member.event.MemberModifyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberModifyUseCase {
    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    // TODO:: Access token 기반 modify 동작 제한해도 좋을듯
    @Transactional
    public Member modify(int id, String username, String password, String nickname) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DomainException("400","존재하지 않는 회원 정보입니다."));

        member.modify(username, password, nickname);

        eventPublisher.publish(new MemberModifyEvent(member.toDto()));

        return member;
    }
}
