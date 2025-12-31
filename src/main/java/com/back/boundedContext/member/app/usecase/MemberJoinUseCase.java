package com.back.boundedContext.member.app.usecase;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.repository.MemberRepository;
import com.back.global.exception.DomainException;
import com.back.global.rsData.RsData;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.back.global.config.GlobalConfig.eventPublisher;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {

    private final MemberRepository memberRepository;

    public RsData<Member> join(String username, String password, String nickname) {
        //중복이 있을 경우
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new DomainException("409-1", "이미 존재하는 username 입니다.");
        });

        //신규 member 등록
        Member member = memberRepository.save(new Member(username, password, nickname));

        //등록 사실을 이벤트로 고지
        // (이벤트는 여기서 발생시키고, 각각의 위치에 있는 MemberJoinEventListener에서 감지해 로직을 실행한다)
        eventPublisher.publish(new MemberJoinedEvent(member.toDto()));

        return new RsData<>("201-1", "%d번 회원이 생성되었습니다.".formatted(member.getId()), member);
    }

}
