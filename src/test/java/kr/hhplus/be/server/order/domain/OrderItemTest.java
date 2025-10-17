package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.order.domain.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {

    @Test
    @DisplayName("상품ID가 null이면 예외 발생")
    void shouldThrowExceptionWhenProductIdIsNull() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(null, "스마트폰", 1, 500000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("productId는 필수입니다.");
    }

    @Test
    @DisplayName("상품명이 null이면 예외 발생")
    void shouldThrowExceptionWhenNameIsNull() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, null, 1, 500000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 필수입니다.");
    }

    @Test
    @DisplayName("상품명이 빈 문자열이면 예외 발생")
    void shouldThrowExceptionWhenNameIsBlank() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, "", 1, 500000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 필수입니다.");
    }

    @Test
    @DisplayName("수량이 0이면 예외 발생")
    void shouldThrowExceptionWhenQuantityIsZero() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, "스마트폰", 0, 500000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("quantity는 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("수량이 음수이면 예외 발생")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, "스마트폰", -1, 500000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("quantity는 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("단가가 0이면 예외 발생")
    void shouldThrowExceptionWhenUnitPriceIsZero() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, "스마트폰", 1, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("unitPrice는 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("단가가 음수이면 예외 발생")
    void shouldThrowExceptionWhenUnitPriceIsNegative() {
        // When & Then
        assertThatThrownBy(() -> new OrderItem(1L, "스마트폰", 1, -1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("unitPrice는 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("주문 항목 생성 성공 테스트")
    void shouldCreateOrderItemSuccessWhenValidDataProvided() {
        // Given
        Long productId = 1L;
        String name = "스마트폰";
        int quantity = 2;
        long unitPrice = 500000L;

        // When
        OrderItem orderItem = new OrderItem(productId, name, quantity, unitPrice);

        // Then
        assertThat(orderItem.getProductId()).isEqualTo(productId);
        assertThat(orderItem.getName()).isEqualTo(name);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity);
        assertThat(orderItem.getUnitPrice()).isEqualTo(unitPrice);
        assertThat(orderItem.getTotalPrice()).isEqualTo(1000000L); // 500000 * 2
    }
}
