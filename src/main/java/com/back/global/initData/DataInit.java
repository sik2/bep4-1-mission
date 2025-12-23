package com.back.global.initData;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.post.app.CommentService;
import com.back.boundedContext.post.app.PostFacade;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Slf4j
public class DataInit {
    private final DataInit self;
    private final MemberFacade memberFacade;
    private final PostFacade postFacade;
    private final CommentService commentService;

    public DataInit(
            @Lazy DataInit self,
            MemberFacade memberFacade,
            PostFacade postFacade, CommentService commentService
    ) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postFacade = postFacade;
        this.commentService = commentService;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.makeBaseMembers();
            self.makeBasePosts();
            self.makeBasePostComments();
        };
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberFacade.count() > 0) return;

        Member systemMember = memberFacade.join("system", "1234", "시스템").getData();
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩").getData();
        Member adminMember = memberFacade.join("admin", "1234", "관리자").getData();
        Member user1Member = memberFacade.join("user1", "1234", "유저1").getData();
        Member user2Member = memberFacade.join("user2", "1234", "유저2").getData();
        Member user3Member = memberFacade.join("user3", "1234", "유저3").getData();
    }

    @Transactional
    public void makeBasePosts() {
        if (postFacade.count() > 0) return;

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        Post post1 = postFacade.write(user1Member, "제목1", "내용1").getData();
        Post post2 = postFacade.write(user1Member, "제목2", "내용2").getData();
        Post post3 = postFacade.write(user1Member, "제목3", "내용3").getData();
        Post post4 = postFacade.write(user2Member, "제목4", "내용4").getData();
        Post post5 = postFacade.write(user2Member, "제목5", "내용5").getData();
        Post post6 = postFacade.write(user3Member, "제목6", "내용6").getData();
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postFacade.findById(1).get();
        Post post2 = postFacade.findById(2).get();
        Post post3 = postFacade.findById(3).get();
        Post post4 = postFacade.findById(4).get();
        Post post5 = postFacade.findById(5).get();
        Post post6 = postFacade.findById(6).get();

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        if (post1.hasComments()) return;

        commentService.addComment(post1, user1Member, "댓글1");
        commentService.addComment(post1, user2Member, "댓글2");
        commentService.addComment(post1, user3Member, "댓글3");

        commentService.addComment(post2, user2Member, "댓글4");
        commentService.addComment(post2, user2Member, "댓글5");

        commentService.addComment(post3, user3Member, "댓글6");
        commentService.addComment(post3, user3Member, "댓글7");

        commentService.addComment(post4, user1Member, "댓글8");
    }
}
