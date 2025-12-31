package com.back.shared.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Comment 도메인의 공유 DTO
 *
 * [설계 원칙]
 * - 도메인 개념(Comment) 기반의 공유 DTO로, 여러 이벤트에서 재사용 가능
 * - 이벤트는 "무슨 일이 일어났는가"를, DTO는 "어떤 데이터가 필요한가"를 표현
 *
 * [새 Payload/DTO를 정의해야 하는 경우]
 * 1. 이벤트별 추가 데이터가 필요한 경우
 * 2. 민감정보를 제외해야 하는 경우
 * 3. 이벤트마다 필요한 필드가 현저히 다른 경우
 *
 * [사용 이벤트]
 * - CommentCreatedEvent: 댓글 생성
 */
@AllArgsConstructor
@Getter
public class CommentDto {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long postId;
    private final Long authorId;
    private final String authorName;
    private final String content;

}

