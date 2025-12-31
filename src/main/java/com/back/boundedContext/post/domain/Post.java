package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name="POST_POST")
public class Post extends BaseIdAndTime {

    private String title;
    @ManyToOne (fetch = LAZY)
    private PostMember author;  // Member → PostMember
    private String content;


    public Post(String title, PostMember author, String content){
        this.title = title;
        this.author = author;
        this.content = content;
    }


    public PostDto toDto() {
        return new PostDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                author.getId(),
                author.getNickname(),
                title,
                content
        );
    }


}
