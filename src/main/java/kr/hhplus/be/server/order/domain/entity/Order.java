package kr.hhplus.be.server.order.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.hhplus.be.server.order.domain.vo.OrderStatus;

/**
 * 주문 애그리게이트 루트
 */
@Getter
public class Order {

    private Long id;
    private final String orderNo;
    private final Long customerId;
    private OrderStatus status;
    private final List<OrderItem> items;
    private long originalAmount; // 할인 전 금액
    private long discountAmount; // 할인 금액
    private Long customerCouponNo; // 사용된 고객 쿠폰 번호 (nullable)
    private long totalAmount; // 최종 결제 금액 (originalAmount - discountAmount)
    private final LocalDateTime createdAt;

    // 생성자
    public Order(String orderNo, Long customerId, List<OrderItem> items) {
        if (orderNo == null || orderNo.isBlank()) {
            throw new IllegalArgumentException("orderNo는 필수입니다.");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("customerId는 필수입니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
        }

        this.orderNo = orderNo;
        this.customerId = customerId;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>(items);
        this.originalAmount = calculateTotalAmount(items);
        this.discountAmount = 0L;
        this.customerCouponNo = null;
        this.totalAmount = this.originalAmount;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 쿠폰을 사용하여 주문을 생성합니다.
     *
     * @param orderNo          주문번호
     * @param customerId       고객 ID
     * @param items            주문 항목
     * @param customerCouponNo 고객 쿠폰 번호 (null 허용)
     * @param discountAmount   할인 금액 (0 이상, 원래 금액 이하)
     */
    public Order(String orderNo, Long customerId, List<OrderItem> items, Long customerCouponNo, long discountAmount) {
        if (orderNo == null || orderNo.isBlank()) {
            throw new IllegalArgumentException("orderNo는 필수입니다.");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("customerId는 필수입니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
        }

        this.orderNo = orderNo;
        this.customerId = customerId;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>(items);
        this.originalAmount = calculateTotalAmount(items);

        if (discountAmount < 0) {
            throw new IllegalArgumentException("할인 금액은 음수일 수 없습니다.");
        }
        if (discountAmount > this.originalAmount) {
            throw new IllegalArgumentException("할인 금액은 원래 금액보다 클 수 없습니다.");
        }
        if (customerCouponNo != null && customerCouponNo <= 0) {
            throw new IllegalArgumentException("쿠폰 번호는 null이거나 양수여야 합니다.");
        }

        this.discountAmount = discountAmount;
        this.customerCouponNo = customerCouponNo;
        this.totalAmount = this.originalAmount - this.discountAmount;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 주문 결제를 완료합니다.
     * PENDING 상태의 주문만 결제 완료가 가능합니다.
     *
     * @throws IllegalStateException 주문이 PENDING 상태가 아닌 경우
     */
    public void completePayment() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("결제는 PENDING 상태에서만 가능합니다.");
        }
        this.status = OrderStatus.PAID;
    }

    /**
     * ID를 설정합니다. (데이터베이스 저장 후 호출)
     *
     * @param id 설정할 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 주문에 포함된 상품 목록을 반환합니다.
     * 반환되는 리스트는 불변(immutable)이므로 외부에서 수정할 수 없습니다.
     *
     * @return 주문 항목 목록 (불변 리스트)
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * 주문에 포함된 모든 상품의 총 가격을 계산합니다.
     * 각 주문 항목의 getTotalPrice()를 모두 합산한 값을 반환합니다.
     *
     * @param items 주문 항목 목록
     * @return 모든 주문 항목의 총 가격 합계
     */
    private long calculateTotalAmount(List<OrderItem> items) {
        long totalAmount = 0L;
        for (OrderItem item : items) {
            totalAmount += item.getTotalPrice();
        }
        return totalAmount;
    }
}
