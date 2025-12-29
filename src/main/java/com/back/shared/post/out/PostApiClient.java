package com.back.shared.post.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.back.shared.post.dto.PostDto;

@Service
public class PostApiClient {
	private final RestClient restClient;

	public PostApiClient(@Value("${custom.global.internalBaseUrl}") String internalBaseUrl) {
		this.restClient = RestClient.builder()
			.baseUrl(internalBaseUrl + "/api/v1/post")
			.build();
	}

	public List<PostDto> getItems()	{
		return restClient.get()
			.uri("/posts")
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});
	}

	public PostDto getItem(int id) {
		return restClient.get()
			.uri("posts/%d".formatted(id))
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});
	}
}
