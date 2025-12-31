package com.back.shared.cash.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.back.shared.cash.dto.WalletDto;

@Service
public class CashApiClient {
	private final RestClient restClient;

	public CashApiClient(@Value("${custom.global.internalBaseUrl}")String internalBaseUrl) {
		this.restClient = RestClient.builder()
			.baseUrl(internalBaseUrl + "/api/v1/cash")
			.build();
	}

	public WalletDto getItemByHolderId(int holderId) {
		return restClient.get()
			.uri("/wallets/by-holder/" + holderId)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});
	}

	public long getBalanceByHolderId(int holderId) {
		WalletDto walletDto = getItemByHolderId(holderId);
		return walletDto.getBalance();
	}
}
