package com.back.boundedContext.member.in;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberScoreEvent;
import com.back.boundedContext.member.app.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final MemberFacade memberFacade;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberScoreEvent memberScoreEvent){
        Member member = memberFacade.findByMemberId(memberScoreEvent.memberId());
        member.increaseScore(memberScoreEvent.activityType().getScore());
    }
}
