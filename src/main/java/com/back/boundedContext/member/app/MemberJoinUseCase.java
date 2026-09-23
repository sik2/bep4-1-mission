package com.back.boundedContext.member.app;

import com.back.global.GlobalConfig;
import com.back.shared.member.dto.MemberDto;
import com.back.global.exception.DomainException;
import com.back.global.response.RsData;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {
    private final MemberRepository memberRepository;

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new DomainException("409-1", "이미 존재하는 username 입니다.");
        });

        Member member = memberRepository.save(new Member(username, password, nickname));

        GlobalConfig.getEventPublisher().publish(new MemberJoinedEvent(member.toDto()));

        return new RsData<>("202-1", "%d번째 멤버가 가입했습니다.".formatted(member.getId()), member);
    }
}
