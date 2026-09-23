package com.back.boundedContext.post.app;

import com.back.global.response.RsData;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.event.CommentCreateEvent;
import com.back.shared.post.event.PostCreatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RecordApplicationEvents
@SpringBootTest
class PostWriteUseCaseTest {
    @Autowired
    PostWriteUseCase postWriteUseCase;

    @Autowired
    PostMemberRepository postMemberRepository;

    @Autowired
    PostRepository postRepository;

    @MockitoBean
    MemberApiClient memberApiClient;

    @Autowired
    ApplicationEvents events;

    @Autowired
    ApplicationEventPublisher publisher;
    @Test
    @DisplayName("게시글 작성 성공 시 활동점수 3점을 얻는다.")
    @Transactional
    void writePostSuccess() {
        // given
        PostMember user1before = postMemberRepository.findByUsername("admin").get();
        int initialScore = user1before.getActivityScore();
        given(memberApiClient.getRandomSecureTip())
                .willReturn("비밀번호 유효기간은 90일입니다.");

        // 게시글 생성후 이벤트 처리 및 db 커밋
        postWriteUseCase.write(user1before, "제목1", "내용1");
        transactionCommitAndStart();

        // then
        // 1. DB 저장 검증
        PostMember postMember = postMemberRepository.findById(user1before.getId()).get();

        // 2. 이벤트 발행 검증
        long postCreatedEventCount = events.stream(PostCreatedEvent.class).count();
        assertThat(postCreatedEventCount).isEqualTo(1);

        // 3. 활동 점수가 3점 상승했는지 검증
        assertThat(postMember.getActivityScore()).isEqualTo(initialScore + 3);
    }

    @Test
    @DisplayName("댓글 작성 성공시 활동점수 1점을 얻는다.")
    @Transactional
    void addPostCommentSuccess() {
        // given
        PostMember user1before = postMemberRepository.findByUsername("admin").get();
        given(memberApiClient.getRandomSecureTip())
                .willReturn("비밀번호 유효기간은 90일입니다.");

        // 게시글 생성후 이벤트 처리 및 db 커밋
        RsData<Post> result = postWriteUseCase.write(user1before, "제목1", "내용1");
        transactionCommitAndStart();

        PostMember memberAfterWrite = postMemberRepository.findById(user1before.getId()).get();
        int scoreAfterWrite = memberAfterWrite.getActivityScore();

        // when
        // 댓글 추가
        Post post = postRepository.findById(result.getData().getId()).get();
        post.addComment(memberAfterWrite, "댓글1");

        // 이벤트 처리 및 db 커밋
        transactionCommitAndStart();

        // then
        // 1. DB 저장 검증
        Post updatedPost = postRepository.findById(post.getId()).get();
        assertThat(updatedPost.getComments()).hasSize(1);

        // 2. 이벤트 발행 검증
        long postCreatedEventCount = events.stream(PostCreatedEvent.class).count();
        long commentCreateEventCount = events.stream(CommentCreateEvent.class).count();

        assertThat(postCreatedEventCount).isEqualTo(1);
        assertThat(commentCreateEventCount).isEqualTo(1);

        // 3. 활동 점수가 1점 상승했는지 검증
        PostMember postMemberFinal = postMemberRepository.findById(user1before.getId()).get();
        assertThat(postMemberFinal.getActivityScore()).isEqualTo(scoreAfterWrite + 1);
    }

    @Test
    @DisplayName("게시글 작성에 실패할 경우 활동점수가 증가하지 않는다. .")
    @Transactional
    void createPostFail() {
        // given
        PostMember user1before = postMemberRepository.findByUsername("admin").get();
        int initialScore = user1before.getActivityScore();
        given(memberApiClient.getRandomSecureTip())
                .willReturn("비밀번호 유효기간은 90일입니다.");
        given(postWriteUseCase.write(user1before, "제목1", "내용1"))
                .willThrow(new IllegalArgumentException());

        // when
        // 게시글 생성 실패
        assertThatThrownBy(()-> postWriteUseCase.write(user1before, "제목1", "내용1"))
                .isInstanceOf(IllegalArgumentException.class);

        // then
        // 1. DB 저장 검증
        PostMember postMember = postMemberRepository.findById(user1before.getId()).get();

        // 2. 이벤트 발행 검증
        long commentCreateEventCount = events.stream(CommentCreateEvent.class).count();
        assertThat(commentCreateEventCount).isEqualTo(0);

        // 3. 활동점수가 증가하지 않았음을 검증
        assertThat(postMember.getActivityScore()).isEqualTo(initialScore);
    }

    @Test
    @DisplayName("댓글 작성에 실패할 경우 활동점수가 증가하지 않는다.")
    @Transactional
    void addPostCommentFail() {
        // given
        PostMember user1before = postMemberRepository.findByUsername("admin").get();
        given(memberApiClient.getRandomSecureTip())
                .willReturn("비밀번호 유효기간은 90일입니다.");

        // 게시글 생성후 이벤트 처리 및 db 커밋
        RsData<Post> result = postWriteUseCase.write(user1before, "제목1", "내용1");
        transactionCommitAndStart();

        PostMember memberAfterWrite = postMemberRepository.findById(user1before.getId()).get();
        int scoreAfterWrite = memberAfterWrite.getActivityScore();

        // when
        // 댓글 추가 실패
        Post post = postRepository.findById(result.getData().getId()).get();
        assertThatThrownBy(()-> post.addComment(null, "댓글1"))
                .isInstanceOf(NullPointerException.class);

        // then
        // 1. DB 저장 검증
        Post updatedPost = postRepository.findById(post.getId()).get();
        assertThat(updatedPost.getComments()).hasSize(1);

        // 2. 이벤트 발행 검증
        long postCreatedEventCount = events.stream(PostCreatedEvent.class).count();
        long commentCreateEventCount = events.stream(CommentCreateEvent.class).count();

        assertThat(postCreatedEventCount).isEqualTo(1);
        assertThat(commentCreateEventCount).isEqualTo(0);

        // 3. 활동 점수가 1점 상승했는지 검증
        PostMember postMemberFinal = postMemberRepository.findById(user1before.getId()).get();
        assertThat(postMemberFinal.getActivityScore()).isEqualTo(scoreAfterWrite);
    }


    @Test
    @DisplayName("회원 가입과 활동점수 변경에 따른 PostMember 동기화를 검증한다.")
    @Transactional
    void postMemberSyncSuccess() {
        // given
        PostMember user1before = postMemberRepository.findByUsername("admin").get();

        MemberJoinedEvent event = new MemberJoinedEvent(new MemberDto(
                user1before.getId(),
                user1before.getUsername(),
                user1before.getNickname(),
                user1before.getActivityScore(),
                user1before.getCreateDate(),
                user1before.getModifyDate()
        ));

        // when
        publisher.publishEvent(event);
        transactionCommitAndStart();

        // then
        PostMember postMember = postMemberRepository.findById(user1before.getId()).get();
        assertThat(user1before.getId()).isEqualTo(postMember.getId());
        assertThat(user1before.getUsername()).isEqualTo(postMember.getUsername());
        assertThat(user1before.getNickname()).isEqualTo(postMember.getNickname());
        assertThat(user1before.getActivityScore()).isEqualTo(postMember.getActivityScore());
        assertThat(user1before.getCreateDate()).isEqualTo(postMember.getCreateDate());
        assertThat(user1before.getModifyDate()).isEqualTo(postMember.getModifyDate());
    }

    void transactionCommitAndStart() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}
