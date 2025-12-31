package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.CommentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name="POST_COMMENT")
public class Comment extends BaseIdAndTime {
    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "post_id")
    Post post;
    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "author_id")
    private PostMember author;  // Member → PostMember
    private String content;

    public Comment (Post post, PostMember author, String content ){
        this.post = post;
        this.author = author;
        this.content = content;
    }
    public CommentDto toDto() {
        return new CommentDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                post.getId(),
                author.getId(),
                author.getNickname(),
                content
        );
    }

}
