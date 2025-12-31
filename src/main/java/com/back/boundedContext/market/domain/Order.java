package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문 도메인 엔티티
 *
 * <p>[설계 원칙]
 * - 장바구니(Cart)로부터 주문을 생성하며, 생성 시점의 상품 정보를 스냅샷으로 보관
 * - 결제 프로세스는 비동기 이벤트 기반으로 처리 (Cash 컨텍스트와 협력)
 * - 주문 상태는 날짜 필드로 관리 (cancelDate, requestPaymentDate, paymentDate)
 *
 * <p>[주문 생명주기]
 * 1. 생성: Cart로부터 Order 생성 (items, price, salePrice 복사)
 * 2. 결제 요청: requestPayment() → MarketOrderPaymentRequestedEvent 발행
 * 3. 결제 처리: Cash 컨텍스트가 이벤트 수신 후 잔액 확인 및 차감
 * 4. 결제 완료: completePayment() 호출로 paymentDate 기록
 * 5. 취소 가능: cancelRequestPayment() 또는 결제 실패 시 처리
 *
 * <p>[LazyLoading 주의사항]
 * - buyer는 지연 로딩됨 (@ManyToOne(fetch = LAZY))
 * - requestPayment() 메서드는 OrderDto 생성 시 buyer.getNickname() 호출
 * - 호출자는 트랜잭션 내에서 buyer를 미리 초기화해야 LazyInitializationException 방지
 *
 * <p>[코드 흐름 예시]
 * <pre>
 * // 주문 생성
 * Order order = new Order(cart);
 *
 * // 결제 요청 (Facade에서 buyer 초기화 필요)
 * order.getBuyer().getNickname(); // 프록시 초기화
 * order.requestPayment(10000L);
 *
 * // 이벤트 발행 → Cash 컨텍스트가 처리
 * // → CashOrderPaymentSucceededEvent 발행
 * // → Market이 수신하여 completePayment() 호출
 * </pre>
 */
@Entity
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
@Getter
public class Order extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private MarketMember buyer;
    private long price;
    private long salePrice;

    private LocalDateTime cancelDate;
    private LocalDateTime requestPaymentDate;
    private LocalDateTime paymentDate;


    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    /**
     * 장바구니로부터 주문을 생성합니다.
     *
     * <p>장바구니의 모든 상품을 주문 항목으로 복사하며,
     * 이후 상품 가격이 변경되어도 주문 금액은 변하지 않습니다.
     *
     * @param cart 주문을 생성할 장바구니
     */
    public Order(Cart cart) {
        this.buyer = cart.getBuyer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    /**
     * 주문에 상품을 추가합니다.
     *
     * <p>상품의 현재 가격 정보를 스냅샷으로 저장하며,
     * 총 가격과 할인 가격을 누적 계산합니다.
     *
     * @param product 추가할 상품
     */
    public void addItem(Product product) {
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );

        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();
    }

    public OrderDto toDto() {
        return new OrderDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                buyer.getId(),
                buyer.getNickname(),
                price,
                salePrice,
                requestPaymentDate,
                paymentDate
        );
    }

    /**
     * 결제를 완료 처리합니다.
     *
     * <p>Cash 컨텍스트에서 결제가 성공하면 이 메서드를 호출하여
     * paymentDate를 기록합니다.
     */
    public void completePayment() {
        paymentDate = LocalDateTime.now();
        publishEvent(
                new MarketOrderPaymentCompletedEvent(
                        toDto()
                )
        );
    }




    /**
     * 결제 완료 여부를 확인합니다.
     *
     * @return 결제가 완료되었으면 true
     */
    public boolean isPaid() {
        return paymentDate != null;
    }

    /**
     * 결제를 요청하고 이벤트를 발행합니다.
     *
     * <p>[중요] 이 메서드는 OrderDto를 생성하므로 buyer가 초기화되어 있어야 합니다.
     * 호출 전에 buyer.getNickname() 등을 호출하여 프록시를 초기화하세요.
     *
     * <p>[이벤트 흐름]
     * 1. MarketOrderPaymentRequestedEvent 발행
     * 2. Cash 컨텍스트가 이벤트 수신
     * 3. 잔액 확인 및 차감
     * 4. CashOrderPaymentSucceededEvent 또는 FailedEvent 발행
     * 5. Market 컨텍스트가 수신하여 결제 완료 또는 취소 처리
     *
     * @param pgPaymentAmount PG사를 통한 결제 금액
     */
    public void requestPayment(long pgPaymentAmount) {
        requestPaymentDate = LocalDateTime.now();

        publishEvent(
                new MarketOrderPaymentRequestedEvent(
                        toDto(),
                        pgPaymentAmount
                )
        );
    }

    /**
     * 결제 요청을 취소합니다.
     *
     * <p>결제 실패 시 호출되며, requestPaymentDate를 null로 초기화합니다.
     */
    public void cancelRequestPayment() {
        requestPaymentDate = null;
    }

    /**
     * 주문을 취소합니다.
     *
     * <p>주문 취소 시 cancelDate를 기록하여 취소 상태로 만듭니다.
     */
    public void cancel() {
        cancelDate = LocalDateTime.now();
    }

    /**
     * 주문 취소 여부를 확인합니다.
     *
     * @return 주문이 취소되었으면 true
     */
    public boolean isCanceled() {
        return cancelDate != null;
    }

    /**
     * 결제 진행 중 여부를 확인합니다.
     *
     * <p>결제 요청은 했지만 아직 완료되지도, 취소되지도 않은 상태입니다.
     *
     * @return 결제 진행 중이면 true
     */
    public boolean isPaymentInProgress() {
        return requestPaymentDate != null && paymentDate == null && cancelDate == null;
    }


}