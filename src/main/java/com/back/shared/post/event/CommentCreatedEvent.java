package com.back.shared.post.event;

import com.back.shared.post.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 생성 이벤트
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - 댓글 데이터는 도메인 기반 공유 DTO(CommentDto)를 사용
 */
@Getter
@AllArgsConstructor
public class CommentCreatedEvent {
    private final CommentDto comment;
}
