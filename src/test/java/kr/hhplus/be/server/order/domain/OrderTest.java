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
}
