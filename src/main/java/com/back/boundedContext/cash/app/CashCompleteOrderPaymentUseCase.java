package com.back.boundedContext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
	private final CashSupport cashSupport;
	private final EventPublisher eventPublisher;

	public void completeOrderPayment(OrderDto orderDto, long pgPaymentAmount) {
		Wallet customerWallet = cashSupport.findWalletByHolderId(orderDto.getCustomerId()).get();
		Wallet holdingWallet = cashSupport.findHoldingWallet().get();

		if (pgPaymentAmount > 0) {
			customerWallet.credit(
				pgPaymentAmount,
				CashLog.EventType.충전__PG결제_토스페이먼츠,
				orderDto.getModelTypeCode(),
				orderDto.getId()
			);
		}

		boolean canPay = customerWallet.getBalance() >= orderDto.getSalePrice();

		if (canPay) {
			customerWallet.debit(
				orderDto.getSalePrice(),
				CashLog.EventType.사용__주문결제,
				orderDto.getModelTypeCode(),
				orderDto.getId()
			);

			holdingWallet.credit(
				orderDto.getSalePrice(),
				CashLog.EventType.임시보관__주문결제,
				orderDto.getModelTypeCode(),
				orderDto.getId()
			);

			eventPublisher.publish(
				new CashOrderPaymentSucceededEvent(
					orderDto,
					pgPaymentAmount
				)

			);
		} else {
			eventPublisher.publish(
				new CashOrderPaymentFailedEvent(
					"400-1",
					"충전은 완료했지만 %번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(orderDto.getId()),
					orderDto,
					pgPaymentAmount,
					pgPaymentAmount - customerWallet.getBalance()
				)
			);
		}
	}
}
