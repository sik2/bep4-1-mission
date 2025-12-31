package com.back.shared.post.dto;

import com.back.boundedContext.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * API 용 응답 DTO(PostResponse)
 * TODO: 각 필드 설명 추가
 */
@AllArgsConstructor(onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES))
@Getter
public class PostResponseDto {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final String content;

    public PostResponseDto(Post post) {
        this(
                post.getId(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent()
        );
    }
}
