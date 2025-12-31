package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_POST_COMMENT")
@NoArgsConstructor
@Getter
public class PostComment extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    PostMember author;

    @ManyToOne(fetch = FetchType.LAZY)
    Post post;

    @Column(columnDefinition = "TEXT")
    String comment;

    PostComment(PostMember author, Post post, String comment) {
        this.author = author;
        this.post = post;
        this.comment = comment;
    }

    public PostCommentDto toDto() {
        return new PostCommentDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                post.getId(),
                author.getId(),
                author.getNickname(),
                comment
        );
    }
}
