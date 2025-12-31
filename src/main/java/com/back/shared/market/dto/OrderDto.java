package com.back.shared.market.dto;

import com.back.standard.modelType.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 주문 정보를 표현하는 공유 DTO
 *
 * <p>[설계 원칙]
 * - 주문 이벤트 발행 시 Order 도메인 데이터를 다른 컨텍스트로 전달
 * - 도메인 엔티티 대신 DTO를 사용하여 컨텍스트 간 결합도 최소화
 *
 * <p>[LazyLoading 주의사항]
 * - Order.buyer는 지연 로딩(@ManyToOne(fetch = LAZY))
 * - 생성자에서 order.getBuyer().getNickname() 호출 시 Hibernate 세션 필요
 * - 반드시 트랜잭션 내에서 DTO를 생성하거나, 사전에 buyer를 초기화해야 함
 *
 * <p>[필드 설명]
 * - id: 주문 ID
 * - createDate: 주문 생성 일시
 * - modifyDate: 주문 수정 일시
 * - customerId: 구매자 ID
 * - customerName: 구매자 닉네임 (buyer 프록시 초기화 필요)
 * - price: 정가 합계
 * - salePrice: 할인가 합계
 * - requestPaymentDate: 결제 요청 일시 (null 가능)
 * - paymentDate: 결제 완료 일시 (null 가능)
 *
 * <p>[사용 흐름]
 * <pre>
 * // MarketFacade.requestPayment() 내에서
 * order.getBuyer().getNickname(); // 프록시 초기화
 * order.requestPayment(10000L);   // OrderDto 생성 → 이벤트 발행
 * </pre>
 *
 * @see com.back.boundedContext.market.domain.Order#requestPayment(long)
 */
@AllArgsConstructor
@Getter
public class OrderDto implements CanGetModelTypeCode {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final Long customerId;
    private final String customerName;
    private final long price;
    private final long salePrice;
    private final LocalDateTime requestPaymentDate;
    private final LocalDateTime paymentDate;

    @Override
    public String getModelTypeCode() {
        return "Order";
    }

}