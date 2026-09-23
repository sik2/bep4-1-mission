package com.back.shared.post.event;


import com.back.shared.post.dto.PostDto;

public class PostCreatedEvent {
    PostDto postDto;

    public PostCreatedEvent(PostDto post) {
        this.postDto = post;
    }

    public PostDto getPostDto() {
        return this.postDto;
    }
}
