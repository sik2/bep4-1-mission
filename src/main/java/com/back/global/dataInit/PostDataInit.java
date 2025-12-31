package com.back.global.dataInit;

import com.back.boundedContext.member.app.facade.MemberFacade;
import com.back.boundedContext.post.app.facade.CommentFacade;
import com.back.boundedContext.post.app.facade.PostFacade;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.rsData.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * 데이터 초기화를 위한 클래스
 */
@Configuration
@Slf4j

public class PostDataInit {
    private final PostDataInit self;
    private final PostFacade postFacade;
    private final CommentFacade commentFacade;

    public PostDataInit(@Lazy PostDataInit self, MemberFacade memberFacade, PostFacade postService, CommentFacade commentFacade) {
        this.self = self;
        this.postFacade = postService;
        this.commentFacade = commentFacade;

    }

    @Bean
    @Order(2)
    public ApplicationRunner PostInitDataRunner() {
        return args -> {
            self.makePosts();
            self.makeComments();
        };
    }

    @Transactional
    public void makePosts() {
        //user1 회원(4번 회원)이 글 3개 작성
        //user2 회원(5번 회원)이 글 2개 작성
        //user3 회원(6번 회원)이 글 1개 작성

        if(postFacade.count()>0) return;

        // PostMember 조회 (Post 컨텍스트는 PostMember만 사용)
        PostMember user1PostMember = postFacade.findPostMemberByUsername("user1").get();
        PostMember user2PostMember = postFacade.findPostMemberByUsername("user2").get();
        PostMember user3PostMember = postFacade.findPostMemberByUsername("user3").get();

        RsData<Post> post1RsData = postFacade.createPost("제목1", user1PostMember,"내용1");
        log.debug(post1RsData.getMsg());

        RsData<Post> post2RsData = postFacade.createPost( "제목2", user1PostMember, "내용2");
        log.debug(post2RsData.getMsg());

        RsData<Post> post3RsData = postFacade.createPost("제목3", user1PostMember,  "내용3");
        log.debug(post3RsData.getMsg());

        RsData<Post> post4RsData = postFacade.createPost("제목4", user2PostMember,  "내용4");
        log.debug(post4RsData.getMsg());

        RsData<Post> post5RsData = postFacade.createPost("제목5", user2PostMember, "내용5");
        log.debug(post5RsData.getMsg());

        RsData<Post> post6RsData = postFacade.createPost("제목6", user3PostMember, "내용6");
        log.debug(post6RsData.getMsg());

    }

    @Transactional
    public void makeComments() {
        //user1 회원이 1번글에 댓글(내용=댓글1) 작성
        //user2 회원이 1번글에 댓글(내용=댓글2) 작성
        //user3 회원이 1번글에 댓글(내용=댓글3) 작성
        //user2 회원이 2번글에 댓글(내용=댓글4) 작성
        //user2 회원이 2번글에 댓글(내용=댓글5) 작성
        //user3 회원이 3번글에 댓글(내용=댓글6) 작성
        //user3 회원이 3번글에 댓글(내용=댓글7) 작성
        //user1 회원이 4번글에 댓글(내용=댓글8) 작성

        if(commentFacade.count()>0) return;

        // PostMember 조회 (Post 컨텍스트는 PostMember만 사용)
        PostMember user1PostMember = postFacade.findPostMemberByUsername("user1").get();
        PostMember user2PostMember = postFacade.findPostMemberByUsername("user2").get();
        PostMember user3PostMember = postFacade.findPostMemberByUsername("user3").get();

        Optional<Post> post1 = postFacade.findByPostId(1);
        Optional<Post> post2 = postFacade.findByPostId(2);
        Optional<Post> post3 = postFacade.findByPostId(3);
        Optional<Post> post4 = postFacade.findByPostId(4);

        commentFacade.createComment(post1.get(), user1PostMember, "댓글1").getData();
        commentFacade.createComment(post1.get(), user2PostMember, "댓글2").getData();
        commentFacade.createComment(post1.get(), user3PostMember, "댓글3").getData();

        commentFacade.createComment(post2.get(), user2PostMember, "댓글4").getData();
        commentFacade.createComment(post2.get(), user2PostMember, "댓글5").getData();

        commentFacade.createComment(post3.get(), user3PostMember, "댓글6").getData();
        commentFacade.createComment(post3.get(), user3PostMember, "댓글7").getData();
//      commentService.createComment(post3.get(), user1Member.get(), "댓글7");
//      왜 값이 다른가 했더니 예제와 값이 달랐음 (예제 쪽에 오타인 듯)

        commentFacade.createComment(post4.get(), user1PostMember, "댓글8");



    }
}