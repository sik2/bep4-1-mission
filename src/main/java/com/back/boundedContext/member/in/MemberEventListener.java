package com.back.boundedContext.member.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
	private final MemberFacade memberFacade;


	// 글 작성 시 점수 3점 올림
	@TransactionalEventListener(phase=AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PostCreatedEvent event) {
		Member member = memberFacade.findById(event.getPost().getAuthorId()).get();

		member.increaseActivityScore(3);
	}

	// 댓글 작성 시 점수 1점 올림
	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PostCommentCreatedEvent event) {
		Member member = memberFacade.findById(event.getPostComment().getAuthorId()).get();

		member.increaseActivityScore(1);
	}
}
