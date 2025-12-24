package com.back.shared.post.event;

import com.back.shared.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCreatedEvent {
    private final PostDto post;
}
