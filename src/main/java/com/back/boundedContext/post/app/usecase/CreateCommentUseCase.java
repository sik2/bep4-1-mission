package com.back.boundedContext.post.app.usecase;

import com.back.boundedContext.post.domain.Comment;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.CommentRepository;
import com.back.boundedContext.post.out.repository.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.post.event.CommentCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCommentUseCase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final EventPublisher eventPublisher;


    @Transactional
    public RsData<Comment> createComment(Post post, PostMember author, String content){
        //새 코멘트를 작성해 DB에 저장하는 로직은 여기서 발생한다. 생성자를 통해 직접 밀어넣는다.
        Comment comment = new Comment(post, author, content);
        commentRepository.save(comment); // 리팩토링 필요함

        //이벤트 발행. 이후의 동작은 이벤트가 담당한다.
        eventPublisher.publish(new CommentCreatedEvent(comment.toDto()));
        return new RsData<>("201-1", "댓글이 성공적으로 생성되었습니다.".formatted(post.getId()), comment);
    }

}
