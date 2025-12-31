package com.back.shared.member.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MemberApiClient {
	private final RestClient restClient;

	public MemberApiClient(@Value("${custom.global.internalBaseUrl}")String internalBaseUrl) {
		this.restClient = RestClient.builder()
			.baseUrl(internalBaseUrl + "/api/v1/member")
			.build();
	}

	public String getRandomSecureTip() {
		return restClient.get()
			.uri("/members/randomSecureTip")
			.retrieve()
			.body(String.class);
	}
}
