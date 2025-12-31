package com.back.shared.post.event;

import com.back.shared.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시글 생성 이벤트
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - 게시글 데이터는 도메인 기반 공유 DTO(PostDto)를 사용
 */
@Getter
@AllArgsConstructor
public class PostCreatedEvent {
    private final PostDto post;
}
