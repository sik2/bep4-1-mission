package com.back.shared.post.out;

import com.back.shared.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PostApiClient {
    private final RestClient restClient;

    public PostApiClient(@Value("${custom.global.internalBackUrl}") String internalBackUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(internalBackUrl + "/api/v1/posts")
                .build();
    }

    public List<PostDto> getItems() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public PostDto getItem(int id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(PostDto.class);
    }
}
