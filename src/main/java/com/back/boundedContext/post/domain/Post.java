package com.back.boundedContext.post.domain;

import com.back.boundedContext.member.domain.Member;
import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.dto.PostCommentDto;
import com.back.shared.post.event.PostCommentCreatedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "POST_POST")
@Getter
public class Post extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    Member author;
    String title;
    @Column(columnDefinition = "LONGTEXT")
    String content;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(Member member, String title, String content) {
        this.author = member;
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(Member author, String content) {
        PostComment postComment = new PostComment(this, author, content);

        comments.add(postComment);

        publishEvent(new PostCommentCreatedEvent(new PostCommentDto(postComment)));

        return postComment;
    }

    public boolean hasComments() {
        return !comments.isEmpty();
    }
}
