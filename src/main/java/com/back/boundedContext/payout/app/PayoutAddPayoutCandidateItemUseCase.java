package com.back.boundedContext.payout.app;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.boundedContext.payout.domain.PayoutEventType;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutCandidateItemRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.dto.OrderItemDto;
import com.back.shared.market.out.MarketApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemUseCase {
	private final MarketApiClient marketApiClient;
	private final PayoutSupport payoutSupport;
	private final PayoutCandidateItemRepository payoutCandidateItemRepository;

	public void addPayoutCandidateItems(OrderDto orderDto) {
		marketApiClient.getOrderItems(orderDto.getId())
			.forEach(orderItemDto -> makePayoutCandidateItems(orderDto, orderItemDto));
	}

	private void makePayoutCandidateItems(
		OrderDto orderDto,
		OrderItemDto orderItemDto
	) {
		PayoutMember system = payoutSupport.findSystemMember().get();
		PayoutMember buyer = payoutSupport.findMemberById(orderItemDto.getBuyerId()).get();
		PayoutMember seller = payoutSupport.findMemberById(orderItemDto.getSellerId()).get();

		makePayoutCandidateItem(
			PayoutEventType.정산__상품판매_수수료,
			orderItemDto.getModelTypeCode(),
			orderItemDto.getId(),
			orderDto.getPaymentTime(),
			buyer,
			system,
			orderItemDto.getPayoutFee()
		);

		makePayoutCandidateItem(
			PayoutEventType.정산__상품판매_대금,
			orderItemDto.getModelTypeCode(),
			orderItemDto.getId(),
			orderDto.getPaymentTime(),
			buyer,
			seller,
			orderItemDto.getSalePriceWithoutFee()
		);

	}

	private void makePayoutCandidateItem(
		PayoutEventType eventType,
		String relTypeCode,
		int relId,
		LocalDateTime paymentTime,
		PayoutMember payer,
		PayoutMember payee,
		long amount
	) {
		PayoutCandidateItem payoutCandidateItem = new PayoutCandidateItem(
			eventType,
			relTypeCode,
			relId,
			paymentTime,
			payer,
			payee,
			amount
		);

		payoutCandidateItemRepository.save(payoutCandidateItem);
	}
}
