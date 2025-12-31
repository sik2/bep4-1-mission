package com.back.boundedContext.member.in.eventListener;

import com.back.boundedContext.member.app.facade.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.global.enums.ScoreEnum;
import com.back.shared.post.event.CommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * Post 컨텍스트의 활동(게시글, 댓글)에 반응하여 Member에게 보상을 지급하는 리스너
 *
 * [설계 원칙]
 * - 이벤트 기반 컨텍스트 간 조율 (Post → Member)
 * - Post 컨텍스트는 PostDto/CommentDto로 이벤트 발행
 * - Member 컨텍스트는 필요한 데이터(authorId)만 사용
 *
 * [이벤트 흐름]
 * PostCreatedEvent/CommentCreatedEvent
 *   → ScoreEventListener.handle()
 *   → Member.increasePoint()
 *   → MemberUpdatedEvent 발행 (도메인에서 자동)
 *   → 각 컨텍스트의 MemberUpdatedEventListener가 수신
 */
@Component
@RequiredArgsConstructor
public class ScoreEventListener {
    private final MemberFacade memberFacade;

    /**
     * 게시글 작성 시 작성자에게 보상 지급
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PostCreatedEvent event) {
        Member member = memberFacade.findById(event.getPost().getAuthorId()).get();
        member.increasePoint(ScoreEnum.POST_CREATE.getScore());
        memberFacade.save(member);
        // 이벤트 발행은 Member 도메인에서 자동으로 처리됨
    }

    /**
     * 댓글 작성 시 작성자에게 보상 지급
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CommentCreatedEvent event) {
        Member member = memberFacade.findById(event.getComment().getAuthorId()).get();
        member.increasePoint(ScoreEnum.COMMENT_CREATE.getScore());
        memberFacade.save(member);
        // 이벤트 발행은 Member 도메인에서 자동으로 처리됨
    }



}