package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.vo.OrderStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    @DisplayName("주문번호가 null이면 예외 발생")
    void shouldThrowExceptionWhenOrderNoIsNull() {
        // Given
        Long customerId = 1L;
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));

        // When & Then
        assertThatThrownBy(() -> new Order(null, customerId, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("orderNo는 필수입니다.");
    }

    @Test
    @DisplayName("주문번호가 빈 문자열이면 예외 발생")
    void shouldThrowExceptionWhenOrderNoIsBlank() {
        // Given
        Long customerId = 1L;
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));

        // When & Then
        assertThatThrownBy(() -> new Order("", customerId, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("orderNo는 필수입니다.");
    }

    @Test
    @DisplayName("고객ID가 null이면 예외 발생")
    void shouldThrowExceptionWhenCustomerIdIsNull() {
        // Given
        String orderNo = "ORD20250101000001";
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));

        // When & Then
        assertThatThrownBy(() -> new Order(orderNo, null, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("customerId는 필수입니다.");
    }

    @Test
    @DisplayName("주문 항목이 비어있으면 예외 발생")
    void shouldThrowExceptionWhenItemsIsEmpty() {
        // Given
        String orderNo = "ORD20250101000001";
        Long customerId = 1L;
        List<OrderItem> items = List.of();

        // When & Then
        assertThatThrownBy(() -> new Order(orderNo, customerId, items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다.");
    }

    @Test
    @DisplayName("PENDING 상태가 아닐 때 결제 완료하면 예외 발생")
    void shouldThrowExceptionWhenPaymentFromNotPendingStatus() {
        // Given
        Order order = createOrder();
        order.completePayment(); // PAID 상태로 변경

        // When & Then
        assertThatThrownBy(() -> order.completePayment())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("결제는 PENDING 상태에서만 가능합니다.");
    }

    @Test
    @DisplayName("PENDING 상태에서 결제 성공 가능 테스트")
    void shouldPaymentOnlySuccessWhenStatusIsPending() {
        // Given
        Order order = createOrder();

        // When
        order.completePayment();

        // Then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
    }

    private Order createOrder() {
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));
        return new Order("ORD20250101000001", 1L, items);
    }

    @Test
    @DisplayName("주문 생성 성공 테스트")
    void shouldOrderCreateSuccessWhenValidDataProvided() {
        // Given
        String orderNo = "ORD20250101000001";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "스마트폰", 2, 500000L),
                new OrderItem(2L, "케이스", 1, 30000L));

        // When
        Order order = new Order(orderNo, customerId, items);

        // Then
        assertThat(order.getOrderNo()).isEqualTo(orderNo);
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(1030000L); // 500000*2 + 30000*1
    }

    // ===== 쿠폰 기능 테스트 =====

    @Test
    @DisplayName("쿠폰 없이 주문 생성 성공 테스트")
    void shouldCreateOrderWhenNoCouponUsed() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 2, 10000L),
                new OrderItem(2L, "상품2", 1, 5000L));

        // when
        Order order = new Order(orderNo, customerId, items);

        // then
        assertThat(order.getOrderNo()).isEqualTo(orderNo);
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getOriginalAmount()).isEqualTo(25000L); // 20000 + 5000
        assertThat(order.getDiscountAmount()).isEqualTo(0L);
        assertThat(order.getTotalAmount()).isEqualTo(25000L); // 원래 금액과 동일
        assertThat(order.getCustomerCouponNo()).isNull();
    }

    @Test
    @DisplayName("고정 금액 할인 쿠폰으로 주문 생성 성공 테스트")
    void shouldCreateOrderWhenUsingFixedAmountCoupon() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 2, 10000L),
                new OrderItem(2L, "상품2", 1, 5000L));
        Long customerCouponNo = 100L;
        long discountAmount = 5000L;

        // when
        Order order = new Order(orderNo, customerId, items, customerCouponNo, discountAmount);

        // then
        assertThat(order.getOriginalAmount()).isEqualTo(25000L);
        assertThat(order.getDiscountAmount()).isEqualTo(5000L);
        assertThat(order.getTotalAmount()).isEqualTo(20000L); // 25000 - 5000
        assertThat(order.getCustomerCouponNo()).isEqualTo(customerCouponNo);
    }

    @Test
    @DisplayName("비율 할인 쿠폰으로 주문 생성 성공 테스트")
    void shouldCreateOrderWhenUsingPercentageCoupon() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 2, 10000L),
                new OrderItem(2L, "상품2", 1, 5000L));
        Long customerCouponNo = 101L;
        long discountAmount = 5000L; // 20% 할인 (25000 * 0.2 = 5000)

        // when
        Order order = new Order(orderNo, customerId, items, customerCouponNo, discountAmount);

        // then
        assertThat(order.getOriginalAmount()).isEqualTo(25000L);
        assertThat(order.getDiscountAmount()).isEqualTo(5000L);
        assertThat(order.getTotalAmount()).isEqualTo(20000L);
        assertThat(order.getCustomerCouponNo()).isEqualTo(customerCouponNo);
    }

    @Test
    @DisplayName("할인 금액이 원래 금액보다 클 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenDiscountAmountGreaterThanOriginalAmount() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 1, 10000L));
        Long customerCouponNo = 100L;
        long discountAmount = 15000L; // 원래 금액보다 큰 할인

        // when & then
        assertThatThrownBy(() -> new Order(orderNo, customerId, items, customerCouponNo, discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 원래 금액보다 클 수 없습니다.");
    }

    @Test
    @DisplayName("할인 금액이 음수일 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenDiscountAmountIsNegative() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 1, 10000L));
        Long customerCouponNo = 100L;
        long discountAmount = -1000L;

        // when & then
        assertThatThrownBy(() -> new Order(orderNo, customerId, items, customerCouponNo, discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 음수일 수 없습니다.");
    }

    @Test
    @DisplayName("쿠폰 번호가 0일 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenCustomerCouponNoIsZero() {
        // given
        String orderNo = "ORDER123";
        Long customerId = 1L;
        List<OrderItem> items = List.of(
                new OrderItem(1L, "상품1", 1, 10000L));
        Long invalidCouponNo = 0L;
        long discountAmount = 1000L;

        // when & then
        assertThatThrownBy(() -> new Order(orderNo, customerId, items, invalidCouponNo, discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 번호는 null이거나 양수여야 합니다.");
    }
}
