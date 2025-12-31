package com.back.boundedContext.post.in.eventListener;

import com.back.boundedContext.post.app.facade.PostFacade;
import com.back.shared.member.event.MemberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * MemberUpdatedEvent를 수신하여 Post 컨텍스트의 PostMember를 갱신하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent와 동일한 syncMember() 호출
 * - JPA merge를 통해 갱신 수행
 */
@Component("postMemberUpdatedEventListener")
@RequiredArgsConstructor
public class MemberUpdatedEventListener {
    private final PostFacade postFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberUpdatedEvent event) {
        postFacade.syncMember(event.getMember());
    }
}
