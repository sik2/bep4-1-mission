package com.back.boundedContext.cash.app.usecase;

import com.back.boundedContext.cash.app.query.CashMemberQuery;
import com.back.boundedContext.cash.app.query.WalletQuery;
import com.back.boundedContext.cash.domain.CashEventType;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashMemberQuery cashMemberQuery;
    private final WalletQuery walletQuery;
    private final EventPublisher eventPublisher;


    public void completeOrderPayment(OrderDto order, long pgPaymentAmount) {
        Wallet customerWallet = walletQuery.findWalletByHolderId(order.getCustomerId()).get();
        Wallet holdingWallet = walletQuery.findHoldingWallet().get();

        if (pgPaymentAmount > 0) {
            customerWallet.credit(
                    pgPaymentAmount,
                    CashEventType.충전__PG결제_토스페이먼츠,
                    order.getModelTypeCode(),
                    order.getId()            );
        }

        boolean canPay = customerWallet.getBalance() >= order.getSalePrice();

        if (canPay) {
            customerWallet.debit(
                    order.getSalePrice(),
                    CashEventType.사용__주문결제,
                    order.getModelTypeCode(),
                    order.getId()
            );

            holdingWallet.credit(
                    order.getSalePrice(),
                    CashEventType.임시보관__주문결제,
                    order.getModelTypeCode(),
                    order.getId()
            );

            eventPublisher.publish(
                    new CashOrderPaymentSucceededEvent(
                            order,
                            pgPaymentAmount
                    )
            );
        } else {
            eventPublisher.publish(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(order.getId()),
                            order,
                            pgPaymentAmount,
                            pgPaymentAmount - customerWallet.getBalance()
                    )
            );
        }
    }
}