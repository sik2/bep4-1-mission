package com.back.boundedContext.post.app.usecase;

import com.back.boundedContext.member.app.facade.MemberFacade;
import com.back.boundedContext.member.out.apiClient.MemberApiClient;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.CommentRepository;
import com.back.boundedContext.post.out.repository.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.post.event.PostCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final EventPublisher eventPublisher;
    private final MemberFacade memberFacade;
    private final MemberApiClient memberApiClient;

    @Transactional
    public RsData<Post> CreatePost(String title, PostMember author, String content){
        //새 포스트를 작성해 DB에 저장하는 로직은 여기서 발생한다. 생성자를 통해 직접 밀어넣는다.
        Post post = postRepository.save(new Post(title, author, content));
        //String randomSecureTip = memberFacade.getRandomSecureTip();

        String randomSecureTip = memberApiClient.getRandomSecureTip(); //API 사용하게 변경

        //이벤트 발행. 이후의 동작은 이벤트가 담당한다.
        eventPublisher.publish(new PostCreatedEvent(post.toDto()));
        return new RsData<>(
                "201-1",
                "%d번 글이 생성되었습니다. 보안 팁 : %s"
                        .formatted(post.getId(), randomSecureTip),post);
    }
}
