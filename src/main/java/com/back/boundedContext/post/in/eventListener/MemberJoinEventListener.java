package com.back.boundedContext.post.in.eventListener;

import com.back.boundedContext.post.app.facade.PostFacade;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * MemberJoinedEvent를 수신하여 Post 컨텍스트에 Member를 동기화하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto 구조 사용
 * - 신규/갱신 판단은 PostSyncUseCase에서 JPA merge로 처리
 */
@Component("postMemberJoinEventListener")
@RequiredArgsConstructor
public class MemberJoinEventListener {
    private final PostFacade postFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        postFacade.syncMember(event.getMember());
    }

}

